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

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Schema(description = "List of products in the cart")
    private List<Product> products;

    // --- CONSTRUCTORS ---

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
        this.products = new ArrayList<>();
    }

    // --- METHODS ---

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getStatus() == ProductStatus.ACTIVE) {
            products.add(product);
        }
    }

    public List<Product> getAllActiveProducts() {
        return products
                .stream()
                .filter(product -> product.getStatus() == ProductStatus.ACTIVE)
                .toList();
    }

    public void removeProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }

    public void clear() {
        products.clear();
    }

    public BigDecimal getActiveProductsTotalCost() {
        return products
                .stream()
                .filter(product -> product.getStatus() == ProductStatus.ACTIVE)
                .map(Product::getPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

    public BigDecimal getActiveProductsAveragePrice() {
        long productCount = products
                .stream()
                .filter(product -> product.getStatus() == ProductStatus.ACTIVE)
                .count();

        if (productCount == 0) {
            return new BigDecimal(0);
        }

        return getActiveProductsTotalCost().divide(new BigDecimal(productCount), RoundingMode.CEILING);
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // --- Equals and hashCode ---

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) &&
                Objects.equals(user, cart.user) &&
                Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, products);
    }

}