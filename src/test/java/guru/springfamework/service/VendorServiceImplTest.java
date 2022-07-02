package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class VendorServiceImplTest {

    @Mock
    VendorRepository repository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendorService = new VendorServiceImpl(repository, VendorMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        // Given
        List<Vendor> vendors = Arrays.asList(
                Vendor.builder().id(1L).name("Vendor 1").build(),
                Vendor.builder().id(2L).name("Vendor 2").build(),
                Vendor.builder().id(3L).name("Vendor 3").build());

        given (repository.findAll()).willReturn(vendors);
        // When
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        // Then
        assertEquals(3, vendorDTOS.size());
        assertEquals("3", vendorDTOS.get(2).getVendorUrl().split("/")[4]);
    }

    @Test
    void getVendorById() {
        Optional<Vendor> vendor = Optional.of(Vendor.builder().id(1L).name("Vendor 1").build());
        given (repository.findById(anyLong())).willReturn(vendor);
        // When
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        // Then
        assertNotNull(vendorDTO, "VendorDTO not found");
        assertEquals("Vendor 1", vendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", vendorDTO.getVendorUrl());
    }

    @Test
    void createNewVendor() {
        // Given
        Vendor vendor = Vendor.builder().id(1L).name("Vendor 1").build();
        VendorDTO vendorDTO = VendorDTO.builder().name("Vendor 1").build();

        given (repository.save(any(Vendor.class))).willReturn(vendor);
        // When
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        // Then
        assertNotNull(savedVendorDTO, "VendorDTO not found");
        assertEquals(savedVendorDTO.getName(), vendor.getName());
        assertEquals("1", savedVendorDTO.getVendorUrl().split("/")[4]);
    }

    @Test
    void saveVendor() {
        // Given
        Vendor vendor = Vendor.builder().id(1L).name("Vendor 1").build();
        VendorDTO vendorDTO = VendorDTO.builder().name("Vendor 1").build();

        when (repository.save(any(Vendor.class))).thenReturn(vendor);
        // When
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        // Then
        assertNotNull(savedVendorDTO, "VendorDTO not found");
        assertEquals(savedVendorDTO.getName(), vendor.getName());
        assertEquals("1", savedVendorDTO.getVendorUrl().split("/")[4]);
    }

    @Test
    void deleteVendorById() {
        // Given

        // When
        vendorService.deleteVendorById(1L);
        // Then
        verify(repository, times(1)).deleteById(anyLong());
    }
}