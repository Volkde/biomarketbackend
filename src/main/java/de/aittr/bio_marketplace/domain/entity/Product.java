package de.aittr.bio_marketplace.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of images for the product, ordered to define the main image (first element)")
    private List<ProductImage> images = new ArrayList<>();

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


    //@JoinColumn(name = "seller_id")
    //@Column(name = "seller_id")
    @ManyToOne
    @NotNull(message = "Seller ID cannot be null")
    @Schema(description = "Seller identifier", example = "1")
    private Seller seller;

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

    public Product(Long id, String title,
                   String description,
                   List<ProductImage> images,
                   ProductUnitOfMeasure unitOfMeasure,
                   BigDecimal price,
                   boolean discounted,
                   ProductStatus status,
                   boolean inStock,
                   Long categoryId,
                   Seller seller,
                   Double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images != null ? images : new ArrayList<>();
        this.unitOfMeasure = unitOfMeasure;
        this.price = price;
        this.discounted = discounted;
        this.status = status;
        this.inStock = inStock;
        this.categoryId = categoryId;
        this.seller = seller;
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

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images != null ? images : new ArrayList<>();
    }

    @Schema(description = "Main image URL for the product (first image in the list)", example = "https://bucket.digitalocean.com/images/banana.jpg")
    public String getImage() {
        return images.isEmpty() ? null : images.get(0).getUrl();
    }

    public void setImage(String imageUrl) {
        if (imageUrl == null) {
            if (!images.isEmpty()) {
                images.remove(0);
            }
        } else {
            if (images.isEmpty()) {
                ProductImage newImage = new ProductImage();
                newImage.setUrl(imageUrl);
                newImage.setProduct(this);
                newImage.setSeller(this.seller);
                images.add(newImage);
            } else {
                images.get(0).setUrl(imageUrl);
            }
        }
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

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
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
        return discounted == product.discounted &&
                inStock == product.inStock &&
                Objects.equals(id, product.id) &&
                Objects.equals(title, product.title) &&
                Objects.equals(description, product.description) &&
                Objects.equals(images, product.images) &&
                unitOfMeasure == product.unitOfMeasure &&
                Objects.equals(price, product.price) &&
                status == product.status &&
                Objects.equals(categoryId, product.categoryId) &&
                Objects.equals(seller, product.seller) &&
                Objects.equals(rating, product.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, images, unitOfMeasure, price, discounted, status, inStock, categoryId, seller, rating);
    }



    /* TODO: add the following fields:
    - attributes
    - reviews
    - dateProduction
    - dateExpiration
*/

}
