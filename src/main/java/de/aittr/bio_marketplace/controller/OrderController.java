package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.requests.CreateOrderRequest;
import de.aittr.bio_marketplace.controller.responses.OrdersResponse;
import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.domain.dto.OrderDto;
import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.service.interfaces.OrderService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order controller", description = "Controller for operations with user orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // --- Create ---

    @Operation(
            summary = "Create orders from cart",
            description = "Creates orders from the current user's cart using the provided buyer address and returns the list of created orders"
    )
    @PostMapping
    public OrdersResponse createOrders(
            @RequestBody CreateOrderRequest request,
            Principal principal) {
        logger.info("Received request to create order with buyerAddress: {}", request.getBuyerAddress());
        List<OrderDto> orders = orderService.createOrdersFromCart(request.getBuyerAddress(), principal.getName());
        logger.info("Created orders: {}", orders);
        return new OrdersResponse(orders);
    }

    // --- Read ---

    @Operation(
            summary = "Get current user's orders",
            description = "Returns the list of orders for the current user where the buyerId matches the user's ID"
    )
    @GetMapping("/user")
    public OrdersResponse getCurrentUsersOrders(Principal principal) {
        logger.info("Received request to get orders for user: {}", principal.getName());
        List<OrderDto> orders = orderService.getOrdersForUser(principal.getName());
        logger.info("Returning orders for user {}: {}", principal.getName(), orders);
        return new OrdersResponse(orders);
    }

    @Operation(
            summary = "Get seller's orders",
            description = "Returns the list of orders for the specified seller (by ID) if the seller is assigned to the current user"
    )
    @GetMapping("/seller/{id}")
    public OrdersResponse getSellersOrders(@PathVariable Long id, Principal principal) {
        logger.info("Received request to get orders for seller ID: {} by user: {}", id, principal.getName());

        UserDto user = userService.getCurrentUserAsDto();
        Long userId = user.getId();

        List<SellerDto> sellers = userService.getAllSellers(userId);
        boolean isSellerAssigned = sellers.stream().anyMatch(seller -> seller.getId().equals(id));
        if (!isSellerAssigned) {
            throw new SecurityException("Seller with ID " + id + " is not assigned to user " + principal.getName());
        }

        List<OrderDto> orders = orderService.getOrdersForSeller(id);
        logger.info("Returning orders for seller ID {}: {}", id, orders);
        return new OrdersResponse(orders);
    }

}