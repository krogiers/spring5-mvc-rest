package guru.springfamework.api.v1.mapper;


import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerURL", ignore = true)
    CustomerDTO CustomerToCustomerDTO(Customer customer);
    @Mapping(target = "id", ignore = true)
    Customer CustomerDTOToCustomer(CustomerDTO customerDTO);
}
