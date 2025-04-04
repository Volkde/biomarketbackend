package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.UserDto;

public class UserResponse {

    private UserDto user;

    public UserResponse(UserDto user) {
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
