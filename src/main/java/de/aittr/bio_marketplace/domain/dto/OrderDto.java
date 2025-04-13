package de.aittr.bio_marketplace.domain.dto;

import de.aittr.bio_marketplace.domain.entity.Order;
import de.aittr.bio_marketplace.domain.entity.OrderItem;
import de.aittr.bio_marketplace.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDto {

    // --- FIELDS ---
    private Long id;
    private OrderStatus status;
    private List<OrderItemDto> items;
    private Long sellerId;
    private AddressDto sellerAddress;
    private Long buyerId;
    private AddressDto buyerAddress;
    private LocalDateTime dateCreated;
    private BigDecimal totalPrice;

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

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public AddressDto getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(AddressDto sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public AddressDto getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(AddressDto buyerAddress) {
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

    public Order toEntity() {
        Order order = new Order();
        order.setId(this.id);
        order.setStatus(this.status);
        order.setSellerId(this.sellerId);
        order.setSellerAddress(this.sellerAddress != null ? this.sellerAddress.toEntity() : null);
        order.setBuyerId(this.buyerId);
        order.setBuyerAddress(this.buyerAddress != null ? this.buyerAddress.toEntity() : null);
        order.setDateCreated(this.dateCreated);
        order.setTotalPrice(this.totalPrice);

        if (this.items != null) {
            List<OrderItem> orderItems = this.items.stream()
                    .map(OrderItemDto::toEntity)
                    .peek(item -> item.setOrder(order))
                    .collect(Collectors.toList());
            order.setItems(orderItems);
        }

        return order;
    }

}