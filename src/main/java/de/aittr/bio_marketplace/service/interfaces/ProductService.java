package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.Product;

import java.util.List;

public interface ProductService {

    // Return all active products
    List<Product> getAllActiveProducts();



    // TODO: Change class type from Product to ProductDto!!!

}
