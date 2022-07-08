package guru.springframework.service;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        return optionalVendor.map(vendor -> { return createVendorDTOObject(vendor);})
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor savedVendor = vendorRepository.save(vendorMapper.vendorDTOToVendorMapper(vendorDTO));
        log.debug("Vendor saved : " + savedVendor);
        return createVendorDTOObject(savedVendor);
    }

    @Override
    public VendorDTO saveVendor(Long vendorId, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendorMapper(vendorDTO);
        vendor.setId(vendorId);

        Vendor savedVendor = vendorRepository.save(vendor);
        log.debug("Vendor saved : " + savedVendor);
        return createVendorDTOObject(savedVendor);
    }

    @Override
    public VendorDTO patchVendor(Long vendorId, VendorDTO vendorDTO) {
        return vendorRepository.findById(vendorId)
                .map(vendor -> {
                    if (vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }
                    return createVendorDTOObject(vendorRepository.save(vendor));
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO createVendorDTOObject(Vendor vendor) {
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTOMapper(vendor);
        //vendorDTO.setVendorUrl(getVendorURL(vendor.getId()));
        return vendorDTO;
    }

    private String getVendorURL(Long vendorId) {
        return VendorController.BASE_URL + vendorId;
    }
}
