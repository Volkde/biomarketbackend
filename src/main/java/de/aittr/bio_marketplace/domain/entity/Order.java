package de.aittr.bio_marketplace.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "totalPrice")
    private BigDecimal totalPrice;

    @Column(name = "dateCreated")
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(Long id, boolean status, BigDecimal totalPrice, LocalDateTime dateCreated, User user) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.dateCreated = dateCreated;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return status == order.status && Objects.equals(id, order.id) && Objects.equals(totalPrice, order.totalPrice) && Objects.equals(dateCreated, order.dateCreated) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, totalPrice, dateCreated, user);
    }

    @Override
    public String toString() {
        return String.format(" Order: ID - %d, Status - %s, Total price - %s, Date created - %s, User - %s.",
                id,  status, totalPrice, dateCreated, user);
    }
}
