package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.domain.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> createOrdersFromCart(AddressDto buyerAddress, String email);
    List<OrderDto> getOrdersForUser(String email);
    public List<OrderDto> getOrdersForSeller(Long sellerId);
    OrderDto getOrderByIdAndUser(Long id, String email);
    List<OrderDto> getAllOrders();
    OrderDto deactivateOrder(Long id, String email);
    OrderDto deactivateSellersOrder(Long orderId, Long sellerId);
    void deleteOrder(Long id, String email);
}