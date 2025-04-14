package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.OrderDto;

import java.util.List;

public class OrdersResponse {
    private List<OrderDto> orders;

    public OrdersResponse(List<OrderDto> orders) {
        this.orders = orders;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}