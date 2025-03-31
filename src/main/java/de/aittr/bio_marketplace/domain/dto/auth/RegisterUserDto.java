package de.aittr.bio_marketplace.domain.dto.auth;

import de.aittr.bio_marketplace.domain.entity.User;

public record RegisterUserDto(
        String email,
        String firstName,
        String lastName,
        String userName,
        String password
) {
}
