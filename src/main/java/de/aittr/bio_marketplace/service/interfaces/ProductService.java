package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    // Save product in DB
    ProductDto save(ProductDto product);

    // Return all active products
    List<ProductDto> getAllActiveProducts();

    // Return active products filtered
    List<ProductDto> getAllActiveProductsFiltered(
            String search,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Long sellerID,
            Double ratingMin,
            Boolean inStock,
            Boolean discounted,
            String sortBy,
            String sortOrder
            );

    // Return active product by id
    ProductDto getById(Long id);

    // Return active product entity by id
    Product getActiveProductEntityById(Long id);

    // Delete product by id
    void deleteById(Long id);

}
