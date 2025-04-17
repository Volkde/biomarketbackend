package de.aittr.bio_marketplace.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Class that describes ProductImage DTO")
public class ProductImageDto {

    // --- FIELDS ---

    @Schema(description = "Image unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Image URL", example = "https://bucket.digitalocean.com/images/product1-image1.jpg")
    private String url;

    @Schema(description = "Product identifier", example = "1")
    private Long productId;

    @Schema(description = "Seller identifier", example = "1")
    private Long sellerId;

    // --- METHODS ---

    // --- Getters and setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    // Equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImageDto that = (ProductImageDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(url, that.url) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(sellerId, that.sellerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, productId, sellerId);
    }
}