package de.aittr.bio_marketplace.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    //статус доставки
    @Column(name = "status", nullable = false)
    private String status;

    //дата доставки может быть null
    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    //Связь с заказом (один заказ имеет одну доставку)
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Delivery() {
    }

    public Delivery(String address, String status, LocalDateTime deliveryDate, Order order) {
        this.address = address;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.order = order;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
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
        if (!(o instanceof Delivery)) return false;
        Delivery that = (Delivery) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(address, that.address) &&
                Objects.equals(status, that.status) &&
                Objects.equals(deliveryDate, that.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, status, deliveryDate);
    }

    @Override
    public String toString() {
        return String.format("Delivery: ID=%d, Address=%s, Status=%s, DeliveryDate=%s",
                id, address, status, deliveryDate);
    }
}
