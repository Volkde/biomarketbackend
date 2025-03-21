package de.aittr.bio_marketplace.exception_handling.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super(String.format("Product with id %s not found", id));
    }

    public ProductNotFoundException(String title) {
        super(String.format("Product with title %s not found", title));
    }
}