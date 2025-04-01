package de.aittr.bio_marketplace.exception_handling.exceptions;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(Long id) {
        super(String.format("Address with id %d not found", id));
    }
}
