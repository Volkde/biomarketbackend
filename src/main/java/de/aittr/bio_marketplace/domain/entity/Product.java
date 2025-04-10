package de.aittr.bio_marketplace.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
@Schema(description = "Class that describes a Product entity")
public class Product {

    // --- FIELDS ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Product unique identifier", example = "777", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotNull(message = "Product title cannot be null")
    @NotBlank(message = "Product title cannot be empty")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "Product title should be at least three characters length and start with capital letter"
    )
    @Schema(description = "Product title", example = "Banana")
    private String title;

    @Column(name = "description")
    @Schema(description = "Product description", example = "Juicy natural banana")
    private String description;

    @Column(name = "image")
    @Schema(description = "Product image URL", example = "https://example.com/images/banana.jpg")
    private String image;

    @Column(name = "unit_of_measure")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Unit of measure for the product", example = "kg")
    private ProductUnitOfMeasure unitOfMeasure;

    @Column(name = "price")
    @DecimalMin(
            value = "1.00",
            message = "Product price should be greater or equal than 1"
    )
    @DecimalMax(
            value = "1000.00",
            inclusive = false,
            message = "Product price should lesser than 1000"
    )
    @Schema(description = "Product price", example = "10.90")
    private BigDecimal price;

    @Column(name = "discounted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Schema(description = "Indicates whether the product has a discount", example = "false")
    private boolean discounted;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Product status", example = "active")
    private ProductStatus status;

    @Column(name = "in_stock", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Schema(description = "Indicates whether the product is currently in stock", example = "true")
    private boolean inStock;

    @Column(name = "category_id")
    @NotNull(message = "Category ID cannot be null")
    @Schema(description = "Category identifier", example = "1")
    private Long categoryId;

    @Column(name = "seller_id")
    @NotNull(message = "Seller ID cannot be null")
    @Schema(description = "Seller identifier", example = "1")
    private Long sellerId;

    @Column(name = "rating")
    @DecimalMin(
            value = "1.00",
            inclusive = true,
            message = "Product rating should be greater or equal than 0"
    )
    @DecimalMax(
            value = "5.00",
            inclusive = true,
            message = "Product price should lesser than 5"
    )
    @Digits(integer = 3, fraction = 2)
    @Schema(description = "Product rating", example = "4.30")
    private Double rating;

    // --- CONSTRUCTORS ---

    public Product() {
    }

    public Product(Long id,
                   String title,
                   String description,
                   String image,
                   ProductUnitOfMeasure unitOfMeasure,
                   BigDecimal price,
                   Boolean discounted,
                   ProductStatus status,
                   Boolean inStock,
                   Long categoryId,
                   Long sellerId,
                   Double rating
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.discounted = discounted;
        this.status = status;
        this.inStock = inStock;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.rating = rating;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public ProductUnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(ProductUnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
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
        Product product = (Product) o;
        return isDiscounted() == product.isDiscounted() && isInStock() == product.isInStock() && Objects.equals(getId(), product.getId()) && Objects.equals(getTitle(), product.getTitle()) && Objects.equals(getDescription(), product.getDescription()) && Objects.equals(getImage(), product.getImage()) && getUnitOfMeasure() == product.getUnitOfMeasure() && Objects.equals(getPrice(), product.getPrice()) && getStatus() == product.getStatus() && Objects.equals(getCategoryId(), product.getCategoryId()) && Objects.equals(getSellerId(), product.getSellerId()) && Objects.equals(getRating(), product.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getImage(), getUnitOfMeasure(), getPrice(), isDiscounted(), getStatus(), isInStock(), getCategoryId(), getSellerId(), getRating());
    }



    /* TODO: add the following fields:
    - images
    - attributes
    - reviews
    - dateProduction
    - dateExpiration
*/

}
