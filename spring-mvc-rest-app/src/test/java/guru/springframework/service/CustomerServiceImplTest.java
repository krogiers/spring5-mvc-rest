package guru.springframework.service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import guru.springframework.repositories.CustomerRepository;
import org.apache.tomcat.util.digester.ArrayStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {
    public static final String FIRST_NAME = "Joe";
    public static final String LAST_NAME = "DOE";
    public static final long ID = 1L;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        // Given
        List<Customer> customers = new ArrayStack<>();
        customers.add(Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).build());
        customers.add(Customer.builder().id(2L).firstName("Wortel").lastName("Ronny").build());

        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // Then
        assertEquals(2, customerDTOS.size());
    }

    @Test
    void getCustomerById() {
        // Given
        Optional <Customer> customer = Optional.of(Customer.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build());

        when(customerRepository.findById(anyLong())).thenReturn(customer);

        // When
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        // Then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
    }

    @Test
    void createNewCustomer() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        Customer savedCustomer = new Customer();
        savedCustomer.setId(ID);
        savedCustomer.setFirstName(FIRST_NAME);
        savedCustomer.setLastName(LAST_NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // When
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        // Then
        assertEquals(customerDTO.getFirstName(), customerDTO.getFirstName());
    }

    @Test
    void saveCustomer() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        Customer savedCustomer = new Customer();
        savedCustomer.setId(ID);
        savedCustomer.setFirstName(FIRST_NAME);
        savedCustomer.setLastName(LAST_NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // When
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        // Then
        assertEquals(customerDTO.getFirstName(), customerDTO.getFirstName());
    }

    @Test
    void deleteCustomer() {
        // Given
        Long id = 1L;

        // When
        customerService.deleteCustomerById(id);

        // Then
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}