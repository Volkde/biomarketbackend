package de.aittr.bio_marketplace.exception_handling.exceptions;

public class ReviewBySellerNotFoundException extends RuntimeException {

    public ReviewBySellerNotFoundException(Long id) {
        super(String.format("Review with seller id %d not found", id));
    }
}
