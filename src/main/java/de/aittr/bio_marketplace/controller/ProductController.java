package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product controller", description = "Controller for various operations with Products")
public class ProductController {

    // --- FIELDS ---

    private final ProductService service;

    // --- CONSTRUCTOR ---

    public ProductController(ProductService service) {
        this.service = service;
    }

    // --- METHODS ---

    // Returns all active products

    @Operation(
            summary = "Get all products",
            description = "Getting all active products that exist in the database"
    )
    @GetMapping("/all")
    public List<Product> getAll() {
        return service.getAllActiveProducts();
    }

    @Operation(
            summary = "Get product by id",
            description = "Getting product from database by id"
    )
    @GetMapping("/{id}")
    public Product getById(
            @PathVariable
            @Parameter(description = "Product unique identifier")
            Long id
    ) {
        return service.getActiveProductEntityById(id);
    }

}

/* TODO:
    - change class Product to ProductDto!!!
    - replace in function getById from getActiveProductEntityById to getById when the last one is ready;

 */