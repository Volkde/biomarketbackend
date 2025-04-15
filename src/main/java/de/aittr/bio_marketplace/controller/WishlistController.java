package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.responses.WishlistResponse;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.dto.WishlistDto;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.interfaces.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/wishlist")
@Tag(name = "Wishlist controller", description = "Controller for operations with user wishlist")
public class WishlistController {

    // --- FIELDS ---

    private final Logger logger = LoggerFactory.getLogger(WishlistController.class);
    private final WishlistService wishlistService;
    private final UserService userService;

    // --- CONSTRUCTOR ---

    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    // --- METHODS ---

    // --- Create ---

    @Operation(
            summary = "Toggle product in wishlist",
            description = "Adds a product to the current user's wishlist if it's not already there, or removes it if it is, and returns the updated wishlist wrapped in a 'wishlist' object"
    )
    @PostMapping("/{productId}")
    public WishlistResponse toggleProductInWishlist(
            @PathVariable
            @Parameter(description = "Product unique identifier")
            Long productId,
            Principal principal) {
        logger.info("Received request to toggle product with ID: {} in wishlist for user: {}", productId, principal.getName());

        UserDto user = userService.getCurrentUserAsDto();
        Long userId = user.getId();

        WishlistDto updatedWishlist = wishlistService.toggleProduct(userId, productId);
        WishlistResponse response = new WishlistResponse(
                updatedWishlist.getId(),
                updatedWishlist.getUserId(),
                updatedWishlist.getProductIds().size(),
                updatedWishlist.getProductIds()
        );

        logger.info("Updated wishlist for user {}: {}", principal.getName(), response);
        return response;
    }

    // --- Read ---

    @Operation(
            summary = "Get current user's wishlist",
            description = "Returns the wishlist of the current user wrapped in a 'wishlist' object"
    )
    @GetMapping
    public WishlistResponse getCurrentUsersWishlist(Principal principal) {
        logger.info("Received request to get wishlist for user: {}", principal.getName());

        UserDto user = userService.getCurrentUserAsDto();
        Long userId = user.getId();

        WishlistDto wishlist = wishlistService.getWishlistByUserId(userId);
        WishlistResponse response = new WishlistResponse(
                wishlist.getId(),
                wishlist.getUserId(),
                wishlist.getProductIds().size(),
                wishlist.getProductIds()
        );

        logger.info("Returning wishlist for user {}: {}", principal.getName(), response);
        return response;
    }

    // --- Delete ---

    @Operation(
            summary = "Clear current user's wishlist",
            description = "Removes all products from the current user's wishlist and returns the updated wishlist wrapped in a 'wishlist' object"
    )
    @DeleteMapping
    public WishlistResponse clearWishlist(Principal principal) {
        logger.info("Received request to clear wishlist for user: {}", principal.getName());

        UserDto user = userService.getCurrentUserAsDto();
        Long userId = user.getId();

        WishlistDto clearedWishlist = wishlistService.clearWishlist(userId);
        WishlistResponse response = new WishlistResponse(
                clearedWishlist.getId(),
                clearedWishlist.getUserId(),
                clearedWishlist.getProductIds().size(),
                clearedWishlist.getProductIds()
        );

        logger.info("Cleared wishlist for user {}: {}", principal.getName(), response);
        return response;
    }

}