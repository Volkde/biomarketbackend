package de.aittr.bio_marketplace.exception_handling.exceptions;

public class UserNotSellerException extends RuntimeException {

    public UserNotSellerException(String message) {
        super(message);
    }
}