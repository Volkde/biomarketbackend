package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.Seller;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    // Save product in DB
    ProductDto save(ProductDto dto, Seller seller);

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

    // Update product
    ProductDto update(ProductDto product, Seller seller);

    ProductDto activateById(Long id);

    ProductDto deactivateById(Long id);

    // Delete product by id
    ProductDto deleteById(Long id);

    void deleteAllBySellerId(Long sellerId);


}
