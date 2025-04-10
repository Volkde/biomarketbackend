package de.aittr.bio_marketplace.exception_handling.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(Long id) {
        super(String.format("Review with id %d not found", id));
    }
}
