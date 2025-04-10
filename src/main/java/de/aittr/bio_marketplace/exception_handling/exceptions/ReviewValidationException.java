package de.aittr.bio_marketplace.exception_handling.exceptions;

public class ReviewValidationException extends RuntimeException{
    public ReviewValidationException(String massage) {
        super(massage);
    }
}
