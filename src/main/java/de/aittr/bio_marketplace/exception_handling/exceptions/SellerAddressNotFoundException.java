package de.aittr.bio_marketplace.exception_handling.exceptions;

public class SellerAddressNotFoundException extends RuntimeException {
    public SellerAddressNotFoundException(String message) {
        super(message);
    }
}