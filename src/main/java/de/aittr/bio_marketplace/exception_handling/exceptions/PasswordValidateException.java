package de.aittr.bio_marketplace.exception_handling.exceptions;

public class PasswordValidateException extends RuntimeException{

    public PasswordValidateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Password validate exception |" + super.getMessage();
    }
}