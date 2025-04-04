package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.SellerDto;

public class SellerResponse {
    private SellerDto seller;

    public SellerResponse(SellerDto seller) {
        this.seller = seller;
    }

    public SellerDto getSeller() {
        return seller;
    }

    public void setSeller(SellerDto seller) {
        this.seller = seller;
    }
}
