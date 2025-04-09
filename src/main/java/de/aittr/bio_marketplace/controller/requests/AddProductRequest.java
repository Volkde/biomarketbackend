package de.aittr.bio_marketplace.controller.requests;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Request body for adding a product to the cart")
public class AddProductRequest {

    @NotNull(message = "Product ID cannot be null")
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long productId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than 0")
    @Schema(description = "Quantity of the product to add", example = "1.5", defaultValue = "1")
    private BigDecimal quantity = BigDecimal.ONE;

    public AddProductRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}