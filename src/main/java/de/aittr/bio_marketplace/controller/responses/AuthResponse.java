package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;

public class AuthResponse {

    private RegisterUserResponseDto user;

    public AuthResponse(RegisterUserResponseDto responseDto) {
        this.user = responseDto;
    }

    public RegisterUserResponseDto getUser() {
        return user;
    }

    public void setUser(RegisterUserResponseDto user) {
        this.user = user;
    }
}
