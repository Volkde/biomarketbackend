package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.CartItem;
import de.aittr.bio_marketplace.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    Cart getById(Long cartId);

    Cart save(Cart cart);

    void addProduct(Long cartId, Long productId, BigDecimal quantity);

    void removeProduct(Long cartId, Long productId);

    void clearCart(Long cartId);

    List<CartItem> getAllItems(Long cartId);
}
