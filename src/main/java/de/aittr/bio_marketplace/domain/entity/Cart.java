package de.aittr.bio_marketplace.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        if ("active".equalsIgnoreCase(product.getStatus())) {
            products.add(product);
        }
    }

    public List<Product> getAllActiveProducts() {
        return products
                .stream()
                .filter(product -> "active".equalsIgnoreCase(product.getStatus()))
                .toList();
    }

    public void removeProductById(Long id) {
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
                .filter(product -> "active".equalsIgnoreCase(product.getStatus()))
                .map(Product::getPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getActiveProductsAveragePrice() {
        long productCount = products
                .stream()
                .filter(product -> "active".equalsIgnoreCase(product.getStatus()))
                .count();

        if (productCount == 0) {
            return new BigDecimal(0);
        }

        return getActiveProductsTotalCost().divide(new BigDecimal(productCount), RoundingMode.CEILING);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(user, cart.user) && Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, products);
    }

    @Override
    public String toString() {
        return String.format("Cart: Id - %d.", id);
    }
}
