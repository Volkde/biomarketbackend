package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.service.interfaces.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
@Tag(name = "Seller controller", description = "Controller for various operations with Sellers")
public class SellerController {

    private final SellerService service;

    public SellerController(SellerService service) {
        this.service = service;
    }

    @Operation(
            summary = "Save seller with given parameters",
            description = "Getting all sellers that exist in the database"
    )
    @PostMapping
    public Seller save(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a Seller")
            Seller seller
    ) {
        return service.saveSeller(seller);
    }


    @GetMapping()
    @Operation(
            summary = "Get all sellers",
            description = "Getting all sellers that exist in the database"
    )
    public List<Seller> getAll() {
        return service.getAllActiveSellers();
    }

    @Operation(
            summary = "Get seller by id",
            description = "Getting seller from database by id"
    )
    @GetMapping("/{id}")
    public Seller getById(@PathVariable
                               @Parameter(description = "Seller unique identifier")
                               Long id
    ) {
        return service.getById(id);
    }

    @Operation(
            summary = "Update seller by id",
            description = "Updating seller from database by id"
    )
    @PutMapping
    public void update(@RequestBody Seller seller) {
        service.update(seller);

    }

    @Operation(
            summary = "Delete seller by id",
            description = "Deletion seller from database by id"
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Operation(
            summary = "Delete seller by storeName",
            description = "Deletion seller from database by storeName"
    )
    @DeleteMapping("/by-storeName/{storeName}")
    public void deleteByTitle(@PathVariable String storeName) {
        service.deleteByStoreName(storeName);
    }
}
