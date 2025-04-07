package de.aittr.bio_marketplace.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }


    public void addProduct(Product product) {
        addProduct(product, 1);
    }


    public void addProduct(Product product, int quantity) {

        for (CartItem ci : items) {
            if (ci.getProduct().getId().equals(product.getId())) {
                ci.setQuantity(ci.getQuantity() + quantity);
                return;
            }
        }
        CartItem newItem = new CartItem(this, product, quantity);
        items.add(newItem);
    }


    public void removeProductById(Long productId) {
        Iterator<CartItem> iter = items.iterator();
        while (iter.hasNext()) {
            CartItem ci = iter.next();
            if (ci.getProduct().getId().equals(productId)) {
                iter.remove();
                break;
            }
        }
    }


    public void clear() {
        items.clear();
    }


    public BigDecimal getActiveProductsTotalCost() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : items) {
            if ("active".equalsIgnoreCase(ci.getProduct().getStatus())) {
                BigDecimal itemCost = ci.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(ci.getQuantity()));
                total = total.add(itemCost);
            }
        }
        return total;
    }


    public BigDecimal getActiveProductsAveragePrice() {
        long count = 0;
        BigDecimal sum = BigDecimal.ZERO;

        for (CartItem ci : items) {
            if ("active".equalsIgnoreCase(ci.getProduct().getStatus())) {
                sum = sum.add(ci.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(ci.getQuantity())));
                count += ci.getQuantity();
            }
        }
        if (count == 0) {
            return BigDecimal.ZERO;
        }
        return sum.divide(BigDecimal.valueOf(count), RoundingMode.CEILING);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
