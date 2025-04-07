package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.domain.dto.CartDto;
import de.aittr.bio_marketplace.service.interfaces.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart controller", description = "Controller for user cart operations")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(
            summary = "Get cart contents for the current user",
            description = "Returns the cart (with items) of the currently logged-in user"
    )
    @GetMapping
    public ResponseEntity<CartDto> getUserCart(Principal principal) {
        CartDto cart = cartService.getCartForUser(principal.getName());
        return ResponseEntity.ok(cart);
    }

    @Operation(
            summary = "Add product to cart",
            description = "Adds a given product (by productId) to the user's cart with specified quantity"
    )
    @PostMapping
    public ResponseEntity<CartDto> addProductToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            Principal principal
    ) {
        CartDto updatedCart = cartService.addProductToCart(principal.getName(), productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(
            summary = "Update product quantity in cart",
            description = "Updates the quantity of a specific product in the user's cart"
    )
    @PutMapping
    public ResponseEntity<CartDto> updateProductQuantity(
            @RequestParam Long productId,
            @RequestParam int quantity,
            Principal principal
    ) {
        CartDto updatedCart = cartService.updateProductQuantity(principal.getName(), productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(
            summary = "Remove product from cart",
            description = "Removes a given product from the user's cart entirely"
    )
    @DeleteMapping("/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(
            @PathVariable Long productId,
            Principal principal
    ) {
        CartDto updatedCart = cartService.removeProductFromCart(principal.getName(), productId);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(
            summary = "Clear cart",
            description = "Removes all products from the user's cart"
    )
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(Principal principal) {
        cartService.clearCart(principal.getName());
        return ResponseEntity.noContent().build();
    }
}
