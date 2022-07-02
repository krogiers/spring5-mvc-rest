package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    public static final String FIRST_NAME = "JOE";
    public static final String LAST_NAME = "DOE";
    public static final Long ID = 3L;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void customerToCustomerDTO() {
        // Given
        Customer Customer = new Customer();
        Customer.setFirstName(FIRST_NAME);
        Customer.setLastName(LAST_NAME);
        Customer.setId(ID);

        // When
        CustomerDTO CustomerDTO = CustomerMapper.INSTANCE.CustomerToCustomerDTO(Customer);

        // Then
        assertEquals(FIRST_NAME, CustomerDTO.getFirstName());
        assertEquals(LAST_NAME, CustomerDTO.getLastName());
    }
    
}