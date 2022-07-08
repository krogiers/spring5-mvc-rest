package guru.springframework.controllers.v1;

import guru.springframework.model.CustomerDTO;
import guru.springframework.model.ObjectFactory;
import guru.springframework.service.CustomerService;
import guru.springframework.service.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class CustomerControllerTest {
      
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;    
    
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getCustomerDTOs() throws Exception {
        // Given
        CustomerDTO custDTO1 = new CustomerDTO();
        custDTO1.setFirstName("first 1");
        custDTO1.setLastName("last 1");

        CustomerDTO custDTO2 = new CustomerDTO();
        custDTO2.setFirstName("first 2");
        custDTO1.setLastName("last 2");
        List<CustomerDTO> customers = Arrays.asList(custDTO1, custDTO2);

        when (customerService.getAllCustomers()).thenReturn(customers);

        // When
        mockMvc.perform(get(CustomerController.BASE_URL + "")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerByName() throws Exception {
        // Given
        CustomerDTO custDTO1 = new CustomerDTO();
        custDTO1.setFirstName("first1");
        custDTO1.setLastName("last1");
        custDTO1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when (customerService.getCustomerById(anyLong())).thenReturn(custDTO1);

        // When
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerByNameNotFound() throws Exception {
        // Given
        when (customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        // When
        mockMvc.perform(get(CustomerController.BASE_URL + "/11321")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Kurt");
        customerDTO.setLastName("Rogiers");
        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setFirstName("Kurt");
        returnCustomerDTO.setLastName("Rogiers");
        returnCustomerDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnCustomerDTO);


        String response = mockMvc.perform(post(CustomerController.BASE_URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andReturn().getResponse().getContentAsString();
        log.info(response);

        mockMvc.perform(post(CustomerController.BASE_URL + "/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Kurt")))
                .andExpect(jsonPath("$.lastname", equalTo("Rogiers")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    void updateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Kurt");
        customerDTO.setLastName("Rogiers");
        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setFirstName("Kurt");
        returnCustomerDTO.setLastName("Rogiers");
        returnCustomerDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.saveCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);

        String response = mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andReturn().getResponse().getContentAsString();
        log.info(response);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andReturn().getResponse().getContentAsString();
        log.info(response);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Kurt")))
                .andExpect(jsonPath("$.lastname", equalTo("Rogiers")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    void patchCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Kurt");
        customerDTO.setLastName("Rogiers");
        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setFirstName("Kurt");
        returnCustomerDTO.setLastName("Rogiers");
        returnCustomerDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.saveCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);

        String response = mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andReturn().getResponse().getContentAsString();
        log.info(response);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Kurt")))
                .andExpect(jsonPath("$.lastname", equalTo("Rogiers")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }
}