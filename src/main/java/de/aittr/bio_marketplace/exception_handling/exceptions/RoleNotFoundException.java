package de.aittr.bio_marketplace.exception_handling.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String role) {
        super(String.format("Role with name %s not found", role));
    }
}
