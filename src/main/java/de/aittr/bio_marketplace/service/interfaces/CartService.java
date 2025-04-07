package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.CartDto;

public interface CartService {

    CartDto getCartForUser(String email);

    CartDto addProductToCart(String email, Long productId, int quantity);

    CartDto updateProductQuantity(String email, Long productId, int quantity);

    CartDto removeProductFromCart(String email, Long productId);

    void clearCart(String email);
}
