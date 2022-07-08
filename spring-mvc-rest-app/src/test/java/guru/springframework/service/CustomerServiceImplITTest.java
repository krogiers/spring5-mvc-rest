package guru.springframework.service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;

import guru.springframework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceImplITTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository     ;

    CustomerService customerService;

    @BeforeEach
    public void setUp() throws Exception {
        log.info("Loading customer Data......");
        log.info(String.valueOf(customerRepository.findAll().size()));

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        String updatedName = "updatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(id).orElseThrow(RuntimeException::new);
        assertNotNull (originalCustomer);

        String originalCustomerName = originalCustomer.getLastName();
        String originalCustomerFirstName = originalCustomer.getFirstName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();
        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertThat(originalCustomerFirstName, not(updatedCustomer.getFirstName()));
        assertThat(originalCustomerName, equalTo(updatedCustomer.getLastName()));
    }

    private long getCustomerIdValue() {
        return customerRepository.findAll().stream().findFirst().get().getId();
    }

    @Test
    public void patchCustomerUpdateLastName() {
        String updatedName = "updatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(id).orElseThrow(RuntimeException::new);
        assertNotNull (originalCustomer);

        String originalCustomerName = originalCustomer.getLastName();
        String originalCustomerFirstName = originalCustomer.getFirstName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();
        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());;
        assertThat(originalCustomerName, not(updatedCustomer.getLastName()));
        assertThat(originalCustomerFirstName, equalTo(updatedCustomer.getFirstName()));
    }

}
