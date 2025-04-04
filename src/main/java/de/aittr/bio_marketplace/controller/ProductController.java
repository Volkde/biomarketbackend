package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.responses.ProductResponse;
import de.aittr.bio_marketplace.controller.responses.ProductsResponse;
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
    public ProductsResponse getAll(
            @RequestParam(value = "search_term", required = false)
            @Parameter(description = "Search term to filter products by title or description (case-insensitive, minimum 2 characters)")
            String search,
            @RequestParam(value = "category_id", required = false)
            @Parameter(description = "Category ID to filter products")
            Long categoryId,
            @RequestParam(value = "min_price", required = false)
            @Parameter(description = "Minimal price to filter products")
            Double minPriceDouble,
            @RequestParam(value = "max_price", required = false)
            @Parameter(description = "Maximal price to filter products")
            Double maxPriceDouble,
            @RequestParam(value = "seller_id", required = false)
            @Parameter(description = "Seller ID to filter products")
            Long sellerId,
            @RequestParam(value = "rating_min", required = false)
            @Parameter(description = "Minimum rating to filter products (between 1.00 and 5.00)")
            Double ratingMin,
            @RequestParam(value = "in_stock", required = false)
            @Parameter(description = "Filter products by stock availability: true (in stock) or false (out of stock)")
            Boolean inStock,
            @RequestParam(value = "discounted", required = false)
            @Parameter(description = "Filter products by discount status: 'true' to show only discounted products, 'false' to show only non-discounted products")
            Boolean discounted,
            @RequestParam(value = "sort_by", required = false)
            @Parameter(description = "Field to sort by: 'title' or 'price'")
            String sortBy,
            @RequestParam(value = "sort_order", required = false, defaultValue = "asc")
            @Parameter(description = "Sort order: 'asc' (ascending) or 'desc' (descending)")
            String sortOrder
            ) {
        BigDecimal minPrice = (minPriceDouble != null) ? BigDecimal.valueOf(minPriceDouble) : null;
        BigDecimal maxPrice = (maxPriceDouble != null) ? BigDecimal.valueOf(maxPriceDouble) : null;
        List<ProductDto> products = service.getAllActiveProductsFiltered(
                search,
                categoryId,
                minPrice,
                maxPrice,
                sellerId,
                ratingMin,
                inStock,
                discounted,
                sortBy,
                sortOrder
        );
        return new ProductsResponse(products);
    }

    // Returns product by id
    @Operation(
            summary = "Get product by id",
            description = "Getting product from database by id, wrapped in a 'product' object"
    )
    @GetMapping("/{id}")
    public ProductResponse getById(
            @PathVariable
            @Parameter(description = "Product unique identifier")
            Long id
    ) {
        ProductDto productDto = service.getById(id);
        return new ProductResponse(productDto);
    }

    // --- Delete ---

    // Deletes product from DB by ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}

/* TODO:
- add sending exceptions description
- add new options of sorting: by rating, popularity, new
*/