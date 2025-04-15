package de.aittr.bio_marketplace.domain.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    // --- FIELDS ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "wishlist_product", joinColumns = @JoinColumn(name = "wishlist_id"))
    @Column(name = "product_id")
    private Set<Long> productIds = new HashSet<>();

    // --- CONSTRUCTORS ---

    public Wishlist() {
    }

    public Wishlist(Long id, Long userId, Set<Long> productIds) {
        this.id = id;
        this.userId = userId;
        this.productIds = productIds != null ? productIds : new HashSet<>();
    }

    // --- METHODS ---

    // --- Getters and setters ---

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

    // --- Equals and hashcode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(id, wishlist.id) &&
                Objects.equals(userId, wishlist.userId) &&
                Objects.equals(productIds, wishlist.productIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productIds);
    }
}