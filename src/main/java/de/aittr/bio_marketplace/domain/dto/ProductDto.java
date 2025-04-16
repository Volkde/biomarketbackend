package de.aittr.bio_marketplace.domain.dto;

import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.ProductUnitOfMeasure;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    @NotNull(message = "Title cannot be null")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @Schema(description = "Product description", example = "Juicy natural banana")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Schema(description = "Main image URL for the product (first image in the list)", example = "https://bucket.digitalocean.com/images/banana.jpg")
    private String image;

    @Schema(description = "List of image URLs for the product", example = "[\"https://bucket.digitalocean.com/images/banana1.jpg\", \"https://bucket.digitalocean.com/images/banana2.jpg\"]")
    private List<String> images = new ArrayList<>();

    @Schema(description = "Unit of measure for the product", example = "kg")
    private ProductUnitOfMeasure unitOfMeasure;

    @Schema(description = "Product price", example = "190.00")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater than or equal to 0")
    private BigDecimal price;

    @Schema(description = "Indicates whether the product has a discount", example = "false")
    private Boolean discounted;

    @Schema(description = "Indicates whether the product is currently in stock", example = "true")
    private Boolean inStock;

    @Schema(description = "Category identifier", example = "1")
    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    @Schema(description = "Seller identifier", example = "1")
    @NotNull(message = "Seller ID cannot be null")
    private Long sellerId;

    @Schema(description = "Product rating", example = "4.30")
    @DecimalMin(value = "0.0", message = "Rating must be between 0 and 5")
    @DecimalMax(value = "5.0", message = "Rating must be between 0 and 5")
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
        if (image != null) {
            if (images.isEmpty()) {
                images.add(image);
            } else {
                images.set(0, image);
            }
        } else {
            if (!images.isEmpty()) {
                images.remove(0);
            }
        }
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images != null ? images : new ArrayList<>();
        this.image = this.images.isEmpty() ? null : this.images.get(0);
    }

    public ProductUnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(ProductUnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Boolean discounted) {
        this.discounted = discounted;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
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

    public Boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    public Boolean isInStock() {
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
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getImage(), that.getImage()) &&
                Objects.equals(getImages(), that.getImages()) &&
                getUnitOfMeasure() == that.getUnitOfMeasure() &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getDiscounted(), that.getDiscounted()) &&
                Objects.equals(getInStock(), that.getInStock()) &&
                Objects.equals(getCategoryId(), that.getCategoryId()) &&
                Objects.equals(getSellerId(), that.getSellerId()) &&
                Objects.equals(getRating(), that.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getImage(), getImages(), getUnitOfMeasure(), getPrice(), getDiscounted(), getInStock(), getCategoryId(), getSellerId(), getRating());
    }
}