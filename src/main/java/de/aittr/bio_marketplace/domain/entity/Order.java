package de.aittr.bio_marketplace.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    // --- FIELDS ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @ManyToOne
    @JoinColumn(name = "seller_address_id", nullable = false)
    private Address sellerAddress;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @ManyToOne
    @JoinColumn(name = "buyer_address_id", nullable = false)
    private Address buyerAddress;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    // --- CONSTRUCTORS ---

    public Order() {
    }

    public Order(Long id, OrderStatus status, Long sellerId, Address sellerAddress,
                 Long buyerId, Address buyerAddress, LocalDateTime dateCreated, BigDecimal totalPrice) {
        this.id = id;
        this.status = status;
        this.sellerId = sellerId;
        this.sellerAddress = sellerAddress;
        this.buyerId = buyerId;
        this.buyerAddress = buyerAddress;
        this.dateCreated = dateCreated;
        this.totalPrice = totalPrice;
    }

    // --- METHODS ---

    // --- Getters and setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Address getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(Address sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Address getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(Address buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    // --- Equals and HashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                status == order.status &&
                Objects.equals(sellerId, order.sellerId) &&
                Objects.equals(sellerAddress, order.sellerAddress) &&
                Objects.equals(buyerId, order.buyerId) &&
                Objects.equals(buyerAddress, order.buyerAddress) &&
                Objects.equals(dateCreated, order.dateCreated) &&
                Objects.equals(totalPrice, order.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, sellerId, sellerAddress, buyerId, buyerAddress, dateCreated, totalPrice);
    }
}
