package guru.springfamework.service;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer (CustomerDTO customerDTO);

    CustomerDTO saveCustomer (Long customerId, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long customerId, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);

}
