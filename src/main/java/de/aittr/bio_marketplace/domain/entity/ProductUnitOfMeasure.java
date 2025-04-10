package de.aittr.bio_marketplace.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductUnitOfMeasure {
    PIECE("piece"),
    GRAM("g"),
    KILOGRAM("kg"),
    LITER("l"),
    DOZEN("dozen");

    private final String value;

    ProductUnitOfMeasure(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ProductUnitOfMeasure fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ProductUnitOfMeasure unit : ProductUnitOfMeasure.values()) {
            if (unit.getValue().equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid unit of measure: " + value);
    }
}