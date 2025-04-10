package de.aittr.bio_marketplace.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    CREATED("Created"),
    CONFIRMED("Confirmed"),
    IN_DELIVERY("In Delivery"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    DELETED("Deleted"),
    PENDING_PAYMENT("Pending Payment"),
    PROCESSING("Processing"),
    SHIPPED("Shipped");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid order status: " + value);
    }
}