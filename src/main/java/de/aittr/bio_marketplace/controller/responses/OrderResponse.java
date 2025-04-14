package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.OrderDto;

public class OrderResponse {

    private final OrderDto order;

    public OrderResponse(OrderDto order) {
        this.order = order;
    }

    public OrderDto getOrder() {
        return order;
    }
}