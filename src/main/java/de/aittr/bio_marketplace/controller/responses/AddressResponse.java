package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.AddressDto;

public class AddressResponse {
    private AddressDto address;

    public AddressResponse(AddressDto address) {
        this.address = address;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}

