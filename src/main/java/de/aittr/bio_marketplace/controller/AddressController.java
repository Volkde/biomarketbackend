package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.service.interfaces.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

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
    public AddressDto getAddressById(
            @PathVariable
            @Parameter(description = "Address unique identifier")
            Long id
    ) {
        return service.getAddressById(id);
    }

    @GetMapping("/all-addresses-by-user-id/{id}")
    public List<AddressDto> getAllAddressesByUserId(@PathVariable Long id) {
        return service.getAllAddressesByUserId(id);
    }

 @GetMapping("/all-addresses-by-seller-id/{id}")
    public List<AddressDto> getAllAddressesBySellerId(@PathVariable Long id) {
        return service.getAllAddressesBySellerId(id);
    }

    @PutMapping
    public void update(@RequestBody AddressDto address) {
        service.update(address);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }

}
