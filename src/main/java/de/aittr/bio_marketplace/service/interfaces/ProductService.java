package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.Product;

import java.util.List;

public interface ProductService {

    // Save product in DB
    Product save(Product product);

    // Return all active products
    List<Product> getAllActiveProducts();

    // Return active product by id
    Product getActiveProductEntityById(Long id);

    // Delete product by id
    void deleteById(Long id);



    // TODO: Change class type from Product to ProductDto!!!

}
