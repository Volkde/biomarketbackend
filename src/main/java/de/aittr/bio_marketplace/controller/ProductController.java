package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    // --- Create ---

    // Saves product in DB
    @Operation(
            summary = "Save product",
            description = "Saving product with given parameters"
    )
    @PostMapping
    public ProductDto save(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a Product")
            ProductDto product) {
        return service.save(product);
    }

    // --- Read ---

    // Returns all active products
    @Operation(
            summary = "Get all products",
            description = "Getting all active products that exist in the database, optionally filtered by search term in title or description and/or by category ID"
    )
    @GetMapping()
    public List<ProductDto> getAll(
            @RequestParam(value = "search-term", required = false)
            @Parameter(description = "Search term to filter products by title or description (case-insensitive, minimum 2 characters)")
            String search,
            @RequestParam(value = "category-id", required = false)
            @Parameter(description = "Category ID to filter products")
            Long categoryId,
            @RequestParam(value = "min-price", required = false)
            @Parameter(description = "Minimal price to filter products")
            Double minPriceDouble,
            @RequestParam(value = "max-price", required = false)
            @Parameter(description = "Maximal price to filter products")
            Double maxPriceDouble
    ) {
        BigDecimal minPrice = (minPriceDouble != null) ? BigDecimal.valueOf(minPriceDouble) : null;
        BigDecimal maxPrice = (maxPriceDouble != null) ? BigDecimal.valueOf(maxPriceDouble) : null;
        return service.getAllActiveProductsFiltered(search, categoryId, minPrice, maxPrice);
    }

    // Returns product by id
    @Operation(
            summary = "Get product by id",
            description = "Getting product from database by id"
    )
    @GetMapping("/{id}")
    public ProductDto getById(
            @PathVariable
            @Parameter(description = "Product unique identifier")
            Long id
    ) {
        return service.getById(id);
    }

    // --- Delete ---

    // Deletes product from DB by ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}

// TODO: add sending exceptions description