package guru.springframework.controllers.v1;

import guru.springframework.model.CustomerDTO;
import guru.springframework.model.CustomerListDTO;
import guru.springframework.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Customer Actions", description = "This is a api to do some Customer actions")
//@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
    @GetMapping
    public ResponseEntity<CustomerListDTO> getCustomerDTOs() {
        return new ResponseEntity<CustomerListDTO> (
                new CustomerListDTO(customerService.getAllCustomers()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerByName(@PathVariable String id) {
        return new ResponseEntity<CustomerDTO> (customerService.getCustomerById(Long.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<CustomerDTO> (customerService.createNewCustomer(customerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<CustomerDTO> (customerService.saveCustomer(Long.valueOf(id),customerDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> partialUpdateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<CustomerDTO> (customerService.patchCustomer(Long.valueOf(id),customerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Long.valueOf(id));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getCustomerDTOs() {
        CustomerListDTO customerListDTO = new CustomerListDTO();
        customerListDTO.getCustomers().addAll(customerService.getAllCustomers());
        return customerListDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerByName(@PathVariable String id) {
        return customerService.getCustomerById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(Long.valueOf(id),customerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO partialUpdateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(Long.valueOf(id),customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Long.valueOf(id));
    }

}