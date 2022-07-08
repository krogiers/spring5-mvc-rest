package guru.springframework.service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.model.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer ->  createCustomerDTOObject(customer))
                .collect(Collectors.toList());
    }

    private CustomerDTO createCustomerDTOObject(Customer customer) {
        CustomerDTO customerDTO = customerMapper.CustomerToCustomerDTO(customer);
        customerDTO.setCustomerUrl(getCustomerURL(customer.getId()));
        return customerDTO;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer ->  createCustomerDTOObject(customer))
                .orElseThrow(ResourceNotFoundException::new);

    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.CustomerDTOToCustomer(customerDTO);
        return saveAndReturnDTO(customer);
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer saveCustomer = customerRepository.save(customer);
        return createCustomerDTOObject(saveCustomer);
    }

    @Override
    public CustomerDTO saveCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer customer = customerMapper.CustomerDTOToCustomer(customerDTO);
        customer.setId(customerId);

        return saveAndReturnDTO(customer);

    }

    @Override
    public CustomerDTO patchCustomer(Long customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId).map(customer -> {
                    if (customerDTO.getFirstName() != null)
                        customer.setFirstName(customerDTO.getFirstName());
                    if (customerDTO.getLastName() != null)
                        customer.setLastName(customerDTO.getLastName());

                    CustomerDTO returnedDTO = customerMapper.CustomerToCustomerDTO(customerRepository.save(customer));
                    returnedDTO.setCustomerUrl(getCustomerURL(customerId));
                    return returnedDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    private String getCustomerURL(Long customerId) {
        return CustomerController.BASE_URL + customerId;
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
