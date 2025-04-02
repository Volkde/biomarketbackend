package de.aittr.bio_marketplace.exception_handling.exceptions;

public class AddressBySellerNotFoundException extends RuntimeException {

    public AddressBySellerNotFoundException(Long id) {
        super(String.format("Address with seller id %d not found", id));
    }
}
