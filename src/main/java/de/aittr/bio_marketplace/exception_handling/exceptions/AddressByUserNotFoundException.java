package de.aittr.bio_marketplace.exception_handling.exceptions;

public class AddressByUserNotFoundException extends RuntimeException {

    public AddressByUserNotFoundException(Long id) {
        super(String.format("Address with user id %d not found", id));
    }
}
