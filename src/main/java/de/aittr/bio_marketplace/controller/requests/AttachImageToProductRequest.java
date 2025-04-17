package de.aittr.bio_marketplace.controller.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for attaching an image to a product")
public class AttachImageToProductRequest {

    @NotNull(message = "Image ID cannot be null")
    @Schema(description = "Unique identifier of the image", example = "1")
    private Long imageId;

    @NotNull(message = "Product ID cannot be null")
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long productId;

    public AttachImageToProductRequest() {
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}