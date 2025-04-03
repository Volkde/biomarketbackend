package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.Order;
import java.util.List;

public interface OrderService {
    List<Order> getOrdersForUser(String email);
    Order createOrder(Order order, String email);
    Order getOrderByIdAndUser(Long id, String email);
    void deleteOrder(Long id, String email);
}