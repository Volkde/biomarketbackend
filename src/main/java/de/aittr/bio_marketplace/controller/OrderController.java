package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.requests.CreateOrderRequest;
import de.aittr.bio_marketplace.controller.responses.OrderResponse;
import de.aittr.bio_marketplace.controller.responses.OrdersResponse;
import de.aittr.bio_marketplace.domain.dto.OrderDto;
import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.entity.Order;
import de.aittr.bio_marketplace.service.interfaces.OrderService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order controller", description = "Controller for operations with user orders")
public class OrderController {

    // --- FIELDS ---

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final UserService userService;

    // --- CONSTRUCTOR ---

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // --- METHODS ---

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

    @Operation(
            summary = "Get all orders (admin)",
            description = "Returns the list of all orders in the system, regardless of their status"
    )
    @GetMapping()
    public OrdersResponse getAllOrders() {
        logger.info("Received request to get all orders (admin)");
        List<OrderDto> orders = orderService.getAllOrders();
        logger.info("Returning all orders: {}", orders);
        return new OrdersResponse(orders);
    }

    // --- Delete ---

    @Operation(
            summary = "Deactivate user's order by ID",
            description = "Changes the status of a user's order to 'DELETED' by its ID, making it inactive without removing it from the database, and returns the deactivated order wrapped in an 'order' object"
    )
    @PutMapping("/user/deactivate/{orderId}")
    public OrderResponse deactivateUsersOrderById(
            @PathVariable
            @Parameter(description = "Order unique identifier")
            Long orderId,
            Principal principal) {
        logger.info("Received request to deactivate order with ID: {} for user: {}", orderId, principal.getName());
        OrderDto deactivatedOrder = orderService.deactivateOrder(orderId, principal.getName());
        logger.info("Deactivated order with ID {}: {}", orderId, deactivatedOrder);
        return new OrderResponse(deactivatedOrder);
    }

    @Operation(
            summary = "Deactivate seller's order by ID",
            description = "Changes the status of a seller's order to 'DELETED' by its ID, making it inactive without removing it from the database, and returns the deactivated order wrapped in an 'order' object"
    )
    @PutMapping("/seller/deactivate/{orderId}")
    public OrderResponse deactivateSellersOrderById(
            @PathVariable
            @Parameter(description = "Order unique identifier")
            Long orderId,
            Principal principal) {
        logger.info("Received request to deactivate seller's order with ID: {} for user: {}", orderId, principal.getName());

        UserDto user = userService.getCurrentUserAsDto();
        Long userId = user.getId();

        List<SellerDto> sellers = userService.getAllSellers(userId);
        if (sellers.isEmpty()) {
            throw new SecurityException("User " + principal.getName() + " has no associated sellers");
        }

        Long orderSellerId = sellers.stream()
                .filter(seller -> orderService.getOrdersForSeller(seller.getId()).stream()
                        .anyMatch(order -> order.getId().equals(orderId)))
                .map(SellerDto::getId)
                .findFirst()
                .orElseThrow(() -> new SecurityException("Order with ID " + orderId + " is not associated with any seller of user " + principal.getName()));

        OrderDto deactivatedOrder = orderService.deactivateSellersOrder(orderId, orderSellerId);
        logger.info("Successfully deactivated seller's order with ID {} for user {}: {}", orderId, principal.getName(), deactivatedOrder);
        return new OrderResponse(deactivatedOrder);
    }

    @Operation(
            summary = "Delete order by ID (admin)",
            description = "Deletes an order from the database by its ID and returns the deleted order wrapped in an 'order' object"
    )
    @DeleteMapping("/{orderId}")
    public OrderResponse deleteOrderById(
            @PathVariable
            @Parameter(description = "Order unique identifier")
            Long orderId) {
        logger.info("Received request to delete order with ID: {}", orderId);
        OrderDto deletedOrder = orderService.deleteOrderById(orderId);
        logger.info("Successfully deleted order with ID {}: {}", orderId, deletedOrder);
        return new OrderResponse(deletedOrder);
    }

}