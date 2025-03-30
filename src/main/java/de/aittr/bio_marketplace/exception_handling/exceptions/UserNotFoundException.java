package de.aittr.bio_marketplace.exception_handling.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("User with id %d not found", id));
    }
}
