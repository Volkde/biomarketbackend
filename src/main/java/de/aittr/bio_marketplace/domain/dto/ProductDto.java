package de.aittr.bio_marketplace.domain.dto;

import de.aittr.bio_marketplace.domain.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "Class that describes Product DTO")
public class ProductDto {

    // --- FIELDS ---

    @Schema(
            description = "Product unique identifier",
            example = "777",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(description = "Product title", example = "Banana")
    private String title;

    @Schema(description = "Product description", example = "Juicy natural banana")
    private String description;

    @Schema(description = "Product image URL", example = "...")
    // TODO: add example of URL
    private String image;

    @Schema(description = "Product price", example = "190.00")
    private BigDecimal price;

    @Schema(description = "Indicates whether the product has a discount", example = "false")
    private boolean discounted;

    @Schema(description = "Indicates whether the product is currently in stock", example = "true")
    private boolean inStock;

    @Schema(description = "Category identifier", example = "1")
    private Long categoryId;

    @Schema(description = "Seller identifier", example = "1")
    private Long sellerId;

    @Schema(description = "Product rating", example = "4.30")
    private Double rating;

    // --- METHODS ---

    // --- Getters and setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    // --- Equals and hashcode ---

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return discounted == that.discounted && inStock == that.inStock && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(image, that.image) && Objects.equals(price, that.price) && Objects.equals(categoryId, that.categoryId) && Objects.equals(sellerId, that.sellerId) && Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, image, price, discounted, inStock, categoryId, sellerId, rating);
    }
}