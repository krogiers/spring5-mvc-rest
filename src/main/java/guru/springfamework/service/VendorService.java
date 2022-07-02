package guru.springfamework.service;

import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();
    VendorDTO getVendorById(Long id);
    VendorDTO createNewVendor (VendorDTO vendorDTO);
    VendorDTO saveVendor (Long vendorId, VendorDTO vendorDTO);
    VendorDTO patchVendor(Long vendorId, VendorDTO vendorDTO);
    void deleteVendorById(Long id);
}
