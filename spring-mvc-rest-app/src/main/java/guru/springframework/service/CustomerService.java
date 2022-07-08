package guru.springframework.service;

import guru.springframework.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer (CustomerDTO customerDTO);

    CustomerDTO saveCustomer (Long customerId, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long customerId, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);

}
