package de.aittr.bio_marketplace.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //связь с продуктом продукт должен быть не null
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Product must not be null")
    private Product product;

    //Кол-во единиц товара в позиции
    @Column(name = "quantity", nullable = false)
    private int quantity;

    //цена позиции product.price * quantity
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    //связь с заказом позиция принадлежит одному заказу
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem() {
    }

    public OrderItem(Product product, int quantity, BigDecimal price, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(product, that.product) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, price);
    }

    @Override
    public String toString() {
        return String.format("OrderItem: ID=%d, Product=%s, Quantity=%d, Price=%s",
                id, product, quantity, price);
    }
}
