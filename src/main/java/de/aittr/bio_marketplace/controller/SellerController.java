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


    @GetMapping("/{id}")
    public Seller getById(@PathVariable
                               @Parameter(description = "Seller unique identifier")
                               Long id
    ) {
        return service.getById(id);
    }

    @PutMapping
    public void update(@RequestBody Seller seller) {
        service.update(seller);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @DeleteMapping("/by-storeName/{storeName}")
    public void deleteByTitle(@PathVariable String storeName) {
        service.deleteByStoreName(storeName);
    }
}
