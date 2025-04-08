package de.aittr.bio_marketplace.exception_handling.exceptions;

public class NumberValidateException extends RuntimeException{

    public NumberValidateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Phone number validate exception| " + super.getMessage();
    }
}