package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {

    VendorMapper vendorMapper;

    @BeforeEach
    void setUp() {
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    void getVendorURL() {
        String convertedURL = VendorMapper.getVendorURL(2L);

        assertEquals(VendorController.BASE_URL + "/2", convertedURL);
    }

    @Test
    void vendorToVendorDTOMapper() {
        Vendor vendor = new Vendor(1L, "Vendor 1");

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTOMapper(vendor);

        assertEquals(VendorController.BASE_URL + "/1", vendorDTO.getVendorUrl());
        assertEquals("Vendor 1", vendorDTO.getName());
    }

    @Test
    void vendorDTOToVendorMapper() {
        VendorDTO vendorDTO = new VendorDTO("Vendor 1", "");

        Vendor vendor = vendorMapper.vendorDTOToVendorMapper(vendorDTO);

        assertEquals("Vendor 1", vendor.getName());
    }
}