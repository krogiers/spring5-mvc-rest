package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    @Named("idToUrl")
    public static String getVendorURL(Long vendorId) {
        return VendorController.BASE_URL + "/" + vendorId;
    }

    @Mapping(source = "id", target = "vendorUrl", qualifiedByName = "idToUrl")
    VendorDTO vendorToVendorDTOMapper(Vendor vendor);

    @Mapping(target = "id", ignore = true)
    Vendor vendorDTOToVendorMapper(VendorDTO vendorDTO);
}
