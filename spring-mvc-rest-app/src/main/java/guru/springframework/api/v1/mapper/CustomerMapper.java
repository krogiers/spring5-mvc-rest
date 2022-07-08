package guru.springframework.api.v1.mapper;


import guru.springframework.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerUrl", ignore = true)
    CustomerDTO CustomerToCustomerDTO(Customer customer);
    @Mapping(target = "id", ignore = true)
    Customer CustomerDTOToCustomer(CustomerDTO customerDTO);
}
