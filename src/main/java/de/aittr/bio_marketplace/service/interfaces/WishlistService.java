package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.WishlistDto;

public interface WishlistService {

    // --- Create ---
    WishlistDto toggleProduct(Long userId, Long productId);

    // --- Read ---
    WishlistDto getWishlistByUserId(Long userId);
}