package de.aittr.bio_marketplace.exception_handling.exceptions;

public class ReviewByProductNotFoundException extends RuntimeException {

    public ReviewByProductNotFoundException(Long id) {
        super(String.format("Review with product id %d not found", id));
    }
}
