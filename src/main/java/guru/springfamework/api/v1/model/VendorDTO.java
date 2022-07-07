package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Vendor",
        description = "Represent a Vendor"
)
public class VendorDTO {
    @Schema(type = "string", description = "The real name of the vendor")
    private String name;
    @JsonProperty("vendor_url")
    @Schema(type = "string", description = "Direct url to the vendor resource")
    private String vendorUrl;
}
