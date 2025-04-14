package de.aittr.bio_marketplace.controller.responses;

import java.util.HashSet;
import java.util.Set;

public class WishlistResponse {

    private Long id;
    private Long userId;
    private int productsQuantity;
    private Set<Long> productIds;

    public WishlistResponse(Long id, Long userId, int productsQuantity, Set<Long> productIds) {
        this.id = id;
        this.userId = userId;
        this.productsQuantity = productsQuantity;
        this.productIds = productIds != null ? productIds : new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(int productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds != null ? productIds : new HashSet<>();
    }
}