package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.responses.CartResponse;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.CartItem;
import de.aittr.bio_marketplace.service.interfaces.CartService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart controller", description = "Controller for operations with the shopping cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    // --- Create ---



    // --- Read ---

    @Operation(
            summary = "Get current user's cart",
            description = "Returns the contents of the current user's shopping cart"
    )
    @GetMapping
    public CartResponse getCurrentUserCart() {
        RegisterUserResponseDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.id();
        Cart cart = userService.getActiveUserEntityById(userId).getCart();

        List<CartResponse.CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartResponse.CartItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getTitle(),
                        item.getProduct().getImage(),
                        item.getQuantity(),
                        item.getProduct().getUnitOfMeasure().getValue(),
                        item.getProduct().getPrice().multiply(item.getQuantity())
                ))
                .collect(Collectors.toList());

        BigDecimal totalCartPrice = cart.getActiveProductsTotalCost();

        CartResponse.CartData cartData = new CartResponse.CartData(
                cart.getId(),
                userId,
                items,
                totalCartPrice
        );

        return new CartResponse(cartData);
    }

    // --- Delete ---

    @Operation(
            summary = "Clear cart",
            description = "Clears all items from the current user's cart"
    )
    @DeleteMapping("/clear")
    public CartResponse clearCart() {
        RegisterUserResponseDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.id();
        Cart cart = userService.getActiveUserEntityById(userId).getCart();
        Long cartId = cart.getId();

        cartService.clearCart(cartId);

        CartResponse.CartData cartData = new CartResponse.CartData(
                cartId,
                userId,
                List.of(),
                BigDecimal.ZERO
        );
        return new CartResponse(cartData);
    }

}