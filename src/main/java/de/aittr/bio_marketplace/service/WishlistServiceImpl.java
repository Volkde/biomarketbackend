package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.WishlistDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.Wishlist;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.repository.WishlistRepository;
import de.aittr.bio_marketplace.service.interfaces.WishlistService;
import de.aittr.bio_marketplace.service.mapping.WishlistMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {

    // --- FIELDS ---

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;

    // --- CONSTRUCTOR ---

    public WishlistServiceImpl(WishlistRepository wishlistRepository,
                               ProductRepository productRepository,
                               WishlistMapper wishlistMapper) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.wishlistMapper = wishlistMapper;
    }

    // --- METHODS ---

    // --- Create ---

    @Override
    public WishlistDto toggleProduct(Long userId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found"));

        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUserId(userId);
                    return wishlistRepository.save(newWishlist);
                });

        if (wishlist.getProductIds().contains(productId)) {
            wishlist.getProductIds().remove(productId);
        } else {
            wishlist.getProductIds().add(productId);
        }

        Wishlist updatedWishlist = wishlistRepository.save(wishlist);
        return wishlistMapper.mapEntityToDto(updatedWishlist);
    }

    // --- Read ---

    @Override
    public WishlistDto getWishlistByUserId(Long userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUserId(userId);
                    return wishlistRepository.save(newWishlist);
                });
        return wishlistMapper.mapEntityToDto(wishlist);
    }

    // --- Delete ---

    @Override
    public WishlistDto clearWishlist(Long userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUserId(userId);
                    return wishlistRepository.save(newWishlist);
                });

        wishlist.getProductIds().clear();
        Wishlist updatedWishlist = wishlistRepository.save(wishlist);
        return wishlistMapper.mapEntityToDto(updatedWishlist);
    }

}