package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.requests.CreateOrderRequest;
import de.aittr.bio_marketplace.controller.responses.OrdersResponse;
import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.domain.dto.OrderDto;
import de.aittr.bio_marketplace.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrdersResponse> createOrders(
            @RequestBody CreateOrderRequest request,
            Principal principal) {
        logger.info("Received request to create order with buyerAddress: {}", request.getBuyerAddress());
        List<OrderDto> orders = orderService.createOrdersFromCart(request.getBuyerAddress(), principal.getName());
        logger.info("Created orders: {}", orders);
        return ResponseEntity.ok(new OrdersResponse(orders));
    }

//    @GetMapping
//    public ResponseEntity<List<OrderDto>> getOrdersForUser(Principal principal) {
//        List<OrderDto> orders = orderService.getOrdersForUser(principal.getName());
//        return ResponseEntity.ok(orders);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id, Principal principal) {
//        OrderDto order = orderService.getOrderByIdAndUser(id, principal.getName());
//        return ResponseEntity.ok(order);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Long id, Principal principal) {
//        orderService.deleteOrder(id, principal.getName());
//        return ResponseEntity.noContent().build();
//    }
}