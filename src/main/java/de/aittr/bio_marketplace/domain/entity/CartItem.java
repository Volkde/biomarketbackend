package de.aittr.bio_marketplace.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "cart_item")
@Schema(description = "Class that describes an item in the shopping cart")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Cart item unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @Schema(description = "Cart this item belongs to", accessMode = Schema.AccessMode.READ_ONLY)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "Product in the cart item")
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Schema(description = "Quantity of the product in the cart", example = "1.5")
    private BigDecimal quantity;

    // --- CONSTRUCTORS ---

    public CartItem() {
    }

    public CartItem(Cart cart, Product product, BigDecimal quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // --- GETTERS AND SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    // --- EQUALS AND HASHCODE ---

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) &&
                Objects.equals(cart, cartItem.cart) &&
                Objects.equals(product, cartItem.product) &&
                Objects.equals(quantity, cartItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cart, product, quantity);
    }
}