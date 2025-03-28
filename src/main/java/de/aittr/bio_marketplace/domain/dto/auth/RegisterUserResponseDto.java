package de.aittr.bio_marketplace.domain.dto.auth;

import de.aittr.bio_marketplace.domain.entity.Role;
import de.aittr.bio_marketplace.domain.entity.User;

import java.util.List;

public record RegisterUserResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String userName,
        List<String> roles
) {

    public static RegisterUserResponseDto fromUser(User user) {
        return new RegisterUserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRoles().stream().map(Role::getTitle).toList()
        );
    }
}
