package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.CartDto;
import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.bio_marketplace.exception_handling.exceptions.UserNotFoundException;
import de.aittr.bio_marketplace.repository.CartRepository;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.CartService;
import de.aittr.bio_marketplace.service.mapping.CartMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDto getCartForUser(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        if (user.getCart() == null) {
            Cart newCart = new Cart(user);
            cartRepository.save(newCart);
            user.setCart(newCart);
        }
        return cartMapper.mapEntityToDto(user.getCart());
    }

    @Override
    public CartDto addProductToCart(String email, Long productId, int quantity) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (user.getCart() == null) {
            Cart newCart = new Cart(user);
            cartRepository.save(newCart);
            user.setCart(newCart);
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        user.getCart().addProduct(product, quantity);

        Cart updated = cartRepository.save(user.getCart());
        return cartMapper.mapEntityToDto(updated);
    }

    @Override
    public CartDto updateProductQuantity(String email, Long productId, int quantity) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (user.getCart() == null) {
            return getCartForUser(email);
        }

        if (quantity <= 0) {
            removeProductFromCart(email, productId);
            return getCartForUser(email);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));


        user.getCart().getItems().stream()
                .filter(ci -> ci.getProduct().equals(product))
                .findFirst()
                .ifPresent(ci -> ci.setQuantity(quantity));

        Cart updated = cartRepository.save(user.getCart());
        return cartMapper.mapEntityToDto(updated);
    }

    @Override
    public CartDto removeProductFromCart(String email, Long productId) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (user.getCart() != null) {
            user.getCart().removeProductById(productId);
            cartRepository.save(user.getCart());
        }
        return getCartForUser(email);
    }

    @Override
    public void clearCart(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        if (user.getCart() != null) {
            user.getCart().clear();
            cartRepository.save(user.getCart());
        }
    }
}
