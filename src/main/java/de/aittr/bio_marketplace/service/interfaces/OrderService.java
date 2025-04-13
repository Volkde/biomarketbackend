package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.domain.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> createOrdersFromCart(AddressDto buyerAddress, String email);
    List<OrderDto> getOrdersForUser(String email);
    OrderDto getOrderByIdAndUser(Long id, String email);
    void deleteOrder(Long id, String email);
}