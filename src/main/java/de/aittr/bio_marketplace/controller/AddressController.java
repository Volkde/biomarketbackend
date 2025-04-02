package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.service.interfaces.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Tag(name = "Address controller", description = "Controller for various operations with Address")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @Operation(
            summary = "Save address",
            description = "Saving address with given parameters"
    )
    @PostMapping
    public AddressDto save(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a Address")
            AddressDto address) {
        return service.save(address);
    }

    @GetMapping
    @Operation(
            summary = "Get all addresses",
            description = "Getting all addresses that exist in the database"
    )
    public List<AddressDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get address by id",
            description = "Getting address from database by id"
    )
    public AddressDto getAddressById(
            @PathVariable
            @Parameter(description = "Address unique identifier")
            Long id
    ) {
        return service.getAddressById(id);
    }

    @Operation(
            summary = "Get all addresses by user id",
            description = "Getting addresses from database by user id"
    )
    @GetMapping("/all-addresses-by-user-id/{id}")
    public List<AddressDto> getAllAddressesByUserId(@PathVariable Long id) {
        return service.getAllAddressesByUserId(id);
    }

    @Operation(
            summary = "Get all addresses by seller id",
            description = "Getting addresses from database by seller id"
    )
    @GetMapping("/all-addresses-by-seller-id/{id}")
    public List<AddressDto> getAllAddressesBySellerId(@PathVariable Long id) {
        return service.getAllAddressesBySellerId(id);
    }

    @Operation(
            summary = "Update address by id",
            description = "Updating address from database by id"
    )
    @PutMapping
    public void update(@RequestBody AddressDto address) {
        service.update(address);
    }

    @Operation(
            summary = "Delete address by id",
            description = "Deletion address from database by id"
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Operation(
            summary = "Delete all addresses",
            description = "Deletion all addresses from database by id"
    )
    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }

}
