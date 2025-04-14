package de.aittr.bio_marketplace.controller.requests;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for creating an order with buyer's delivery address")
public class CreateOrderRequest {

    @NotNull(message = "Buyer address cannot be null")
    @Schema(description = "Delivery address of the buyer", required = true)
    private AddressDto buyerAddress;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(AddressDto buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public AddressDto getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(AddressDto buyerAddress) {
        this.buyerAddress = buyerAddress;
    }
}