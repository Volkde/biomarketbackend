package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;

public class SellerResponse {
    private SellerDto seller;
    private UserDto user;

    public SellerResponse(SellerDto seller) {
        this.seller = seller;
    }

    public SellerResponse(SellerDto seller, UserDto user) {
        this.seller = seller;
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

    public SellerDto getSeller() {
        return seller;
    }

    public void setSeller(SellerDto seller) {
        this.seller = seller;
    }
}
