package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(VendorController.BASE_URL)
@Tag(name = "Vendor Actions", description = "This is a api to do some Vendor actions")
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";
    
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Operation(summary = "Get all the vendors",
            description = "This operation is helping you to get all the vendors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the vendors",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VendorListDTO.class)) })
                })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getVendorDTOs() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @Operation( summary = "Get a vendor by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the vendor",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VendorDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Vendor not found",
                    content = @Content) })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorByName(@PathVariable String id) {
        return vendorService.getVendorById(Long.valueOf(id));
    }

    @Operation( summary = "Store a vendor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Store the vendor",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VendorDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid vendor id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Vendor not found",
                    content = @Content) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO VendorDTO) {
        return vendorService.createNewVendor(VendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable String id, @RequestBody VendorDTO VendorDTO) {
        return vendorService.saveVendor(Long.valueOf(id),VendorDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO partialUpdateVendor(@PathVariable String id, @RequestBody VendorDTO VendorDTO) {
        return vendorService.patchVendor(Long.valueOf(id),VendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable String id) {
        vendorService.deleteVendorById(Long.valueOf(id));
    }

}
