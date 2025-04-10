package de.aittr.bio_marketplace.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
@Schema(description = "Class that describes a shopping cart entity")
public class Cart {

    // --- FIELDS ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Cart unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "User associated with the cart", accessMode = Schema.AccessMode.READ_ONLY)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of items in the cart")
    private List<CartItem> items = new ArrayList<>();

    // --- CONSTRUCTORS ---

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
        this.items = new ArrayList<>();
    }

    // --- METHODS ---

    public void addProduct(Product product, BigDecimal quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (product.getStatus() == ProductStatus.ACTIVE) {
            for (CartItem item : items) {
                if (item.getProduct().getId().equals(product.getId())) {
                    item.setQuantity(item.getQuantity().add(quantity));
                    return;
                }
            }
            items.add(new CartItem(this, product, quantity));
        }
    }

    public List<CartItem> getAllActiveItems() {
        return items
                .stream()
                .filter(item -> item.getProduct().getStatus() == ProductStatus.ACTIVE)
                .toList();
    }

    public void removeProductById(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getProduct().getId().equals(productId)) {
                iterator.remove();
                break;
            }
        }
    }

    public void clear() {
        items.clear();
    }

    public BigDecimal getActiveProductsTotalCost() {
        return items
                .stream()
                .filter(item -> item.getProduct().getStatus() == ProductStatus.ACTIVE)
                .map(item -> item.getProduct().getPrice().multiply(item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getActiveProductsAveragePrice() {
        BigDecimal totalQuantity = items
                .stream()
                .filter(item -> item.getProduct().getStatus() == ProductStatus.ACTIVE)
                .map(CartItem::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalQuantity.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return getActiveProductsTotalCost().divide(totalQuantity, RoundingMode.CEILING);
    }

    // --- Getters and setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }


    // --- Equals and hashCode ---


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(getId(), cart.getId()) && Objects.equals(getUser(), cart.getUser()) && Objects.equals(getItems(), cart.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getItems());
    }
}