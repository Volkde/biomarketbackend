package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.CartItem;
import de.aittr.bio_marketplace.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    // --- Create ---
    Cart save(Cart cart);
    void addProduct(Long cartId, Long productId, BigDecimal quantity);

    // --- Read ---
    Cart getById(Long cartId);
    List<CartItem> getAllItems(Long cartId);
    Cart getCartByUserId(Long userId);

    // --- Update ---
    void updateProductQuantity(Long cartId, Long productId, BigDecimal quantity);

    // --- Delete ---
    void removeProduct(Long cartId, Long productId);
    void clearCart(Long cartId);


}
