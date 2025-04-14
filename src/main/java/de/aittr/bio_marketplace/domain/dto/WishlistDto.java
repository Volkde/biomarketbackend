package de.aittr.bio_marketplace.domain.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WishlistDto {

    private Long id;
    private Long userId;
    private Set<Long> productIds;

    public WishlistDto() {
        this.productIds = new HashSet<>();
    }

    public WishlistDto(Long id, Long userId, Set<Long> productIds) {
        this.id = id;
        this.userId = userId;
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

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds != null ? productIds : new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistDto that = (WishlistDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(productIds, that.productIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productIds);
    }
}