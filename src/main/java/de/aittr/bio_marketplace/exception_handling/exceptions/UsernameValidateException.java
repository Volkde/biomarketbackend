package de.aittr.bio_marketplace.exception_handling.exceptions;

public class UsernameValidateException extends RuntimeException{

    public UsernameValidateException(String massage) {
        super(massage);
    }
}
