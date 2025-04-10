package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.CartItem;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.repository.CartRepository;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.service.interfaces.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // --- Create ---

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void addProduct(Long cartId, Long productId, BigDecimal quantity) {
        Cart cart = getById(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found, id=" + productId));

        cart.addProduct(product, quantity);
        cartRepository.save(cart);
    }

    // --- Read ---

    @Override
    public List<CartItem> getAllItems(Long cartId) {
        Cart cart = getById(cartId);
        return cart.getItems();
    }

    @Override
    public Cart getById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found, id=" + cartId));
    }

    // --- Update ---

    @Override
    public void updateProductQuantity(Long cartId, Long productId, BigDecimal quantity) {
        Cart cart = getById(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found, id=" + productId));

        CartItem itemToUpdate = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found in cart"));

        itemToUpdate.setQuantity(quantity);
        cartRepository.save(cart);
    }

    // --- Delete ---

    @Override
    public void removeProduct(Long cartId, Long productId) {
        Cart cart = getById(cartId);
        cart.removeProductById(productId);
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = getById(cartId);
        cart.clear();
        cartRepository.save(cart);
    }

}
