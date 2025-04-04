package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;

public class AuthResponse {

    private RegisterUserResponseDto responseDto;

    public AuthResponse(RegisterUserResponseDto responseDto) {
        this.responseDto = responseDto;
    }

    public RegisterUserResponseDto getResponseDto() {
        return responseDto;
    }

    public void setResponseDto(RegisterUserResponseDto responseDto) {
        this.responseDto = responseDto;
    }
}
