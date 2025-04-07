package de.aittr.bio_marketplace.domain.dto.auth;

public record RegisterUserDto(
        String email,
        String firstName,
        String lastName,
        String username,
        String password
) {
}
