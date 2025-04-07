package de.aittr.bio_marketplace.domain.dto.auth;

import java.util.List;

public record RegisterUserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String username,
        List<String> roles
) {

}
