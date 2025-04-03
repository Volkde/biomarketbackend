package de.aittr.bio_marketplace.exception_handling.exceptions;

public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException(Long id) {
        super(String.format("Seller with id %d not found", id));
    }
    public SellerNotFoundException(String storeName) {
        super(String.format("Seller with Store name %s not found", storeName));
    }
}
