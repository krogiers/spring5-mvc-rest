package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.repositories.VendorRepository;
import guru.springfamework.service.VendorService;
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

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class VendorControllerTest {

    @Mock
    VendorService vendorService;
    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();        
    }

    @Test
    void getVendorDTOs() throws Exception {
        // Given
        VendorDTO vendorDTO1 = VendorDTO.builder().name("Cate 1").build();
        VendorDTO vendorDTO2 = VendorDTO.builder().name("Cate 2").build();
        List<VendorDTO> vendors = Arrays.asList(vendorDTO1, vendorDTO2);

        when (vendorService.getAllVendors()).thenReturn(vendors);

        // When
        mockMvc.perform(get(VendorController.BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
        
    }

    @Test
    void getVendorByName() throws Exception {
        // Given
        VendorDTO vendorDTO1 = VendorDTO.builder().name("Cate 1").build();

        when (vendorService.getVendorById(anyLong())).thenReturn(vendorDTO1);

        // When
        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createVendor() throws Exception {
        // Given
        VendorDTO vendorDTO = VendorDTO.builder().name("Cate 1").build();
        VendorDTO returnVendorDTO = VendorDTO.builder().name("Cate 1").vendorUrl(VendorController.BASE_URL + "/1").build();

        when (vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returnVendorDTO);

        // When
        String response = mockMvc.perform(post(VendorController.BASE_URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andReturn().getResponse().getContentAsString();
        log.info(response);

        mockMvc.perform(post(VendorController.BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Cate 1")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));;
    }

    @Test
    void updateVendor() {
    }

    @Test
    void partialUpdateVendor() {
    }

    @Test
    void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}