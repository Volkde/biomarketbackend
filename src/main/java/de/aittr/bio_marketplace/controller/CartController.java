package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.requests.AddProductRequest;
import de.aittr.bio_marketplace.controller.requests.UpdateCartRequest;
import de.aittr.bio_marketplace.controller.responses.CartResponse;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.CartItem;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.ProductStatus;
import de.aittr.bio_marketplace.service.interfaces.CartService;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart controller", description = "Controller for operations with the shopping cart")
@Validated
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    // --- Create ---

    @Operation(
            summary = "Add product to cart",
            description = "Adds a product with the specified quantity to the current user's cart and returns the updated cart"
    )
    @PostMapping("/add")
    public CartResponse addProductToCart(
            @Valid @RequestBody AddProductRequest request) {
        RegisterUserResponseDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.id();
        Cart cart = userService.getActiveUserEntityById(userId).getCart();
        Long cartId = cart.getId();

        Product product = productService.getActiveProductEntityById(request.getProductId());
        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new IllegalArgumentException("Product with ID " + request.getProductId() + " is not active");
        }

        cartService.addProduct(cartId, request.getProductId(), request.getQuantity());

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
                cartId,
                userId,
                items,
                totalCartPrice
        );

        return new CartResponse(cartData);
    }

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

    // --- Update ---

    @Operation(
            summary = "Update product quantity in cart",
            description = "Updates the quantity of a specific product in the current user's cart and returns the updated cart"
    )
    @PutMapping("/update")
    public CartResponse updateProductQuantity(
            @Valid @RequestBody UpdateCartRequest request) {
        RegisterUserResponseDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.id();
        Cart cart = userService.getActiveUserEntityById(userId).getCart();
        Long cartId = cart.getId();

        Product product = productService.getActiveProductEntityById(request.getProductId());
        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new IllegalArgumentException("Product with ID " + request.getProductId() + " is not active");
        }

        cartService.updateProductQuantity(cartId, request.getProductId(), request.getQuantity());

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
                cartId,
                userId,
                items,
                totalCartPrice
        );

        return new CartResponse(cartData);
    }

    // --- Delete ---

    @Operation(
            summary = "Remove product from cart",
            description = "Removes a specific product from the current user's cart and returns the updated cart"
    )
    @DeleteMapping("/remove/{productId}")
    public CartResponse removeProductFromCart(
            @PathVariable Long productId) {
        RegisterUserResponseDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.id();
        Cart cart = userService.getActiveUserEntityById(userId).getCart();
        Long cartId = cart.getId();

        cartService.removeProduct(cartId, productId);

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
                cartId,
                userId,
                items,
                totalCartPrice
        );

        return new CartResponse(cartData);
    }

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