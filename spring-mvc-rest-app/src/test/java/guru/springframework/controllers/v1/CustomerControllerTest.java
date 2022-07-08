package guru.springframework.controllers.v1;

import guru.springframework.model.CustomerDTO;
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
        CustomerDTO custDTO1 = CustomerDTO.builder().firstName("first 1").lastName("last 1").build();
        CustomerDTO custDTO2 = CustomerDTO.builder().firstName("first 2").lastName("last 2").build();
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
        CustomerDTO custDTO1 = CustomerDTO.builder()
                .firstName("first1")
                .lastName("last1")
                .customerURL(CustomerController.BASE_URL + "/1")
                .build();

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
        CustomerDTO customerDTO = CustomerDTO.builder().firstName("Kurt").lastName("Rogiers").build();
        CustomerDTO returnCustomerDTO = CustomerDTO.builder().firstName("Kurt").lastName("Rogiers").customerURL(CustomerController.BASE_URL + "/1").build();

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
        CustomerDTO customerDTO = CustomerDTO.builder().firstName("Kurt").lastName("Rogiers").build();
        CustomerDTO returnCustomerDTO = CustomerDTO.builder().firstName("Kurt").lastName("Rogiers").customerURL(CustomerController.BASE_URL + "/1").build();

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Kurt")))
                .andExpect(jsonPath("$.lastname", equalTo("Rogiers")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    void patchCustomer() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().firstName("Kurt").lastName("Rogiers").build();
        CustomerDTO returnCustomerDTO = CustomerDTO.builder().firstName("Kurt").lastName("Rogiers").customerURL(CustomerController.BASE_URL + "/1").build();

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