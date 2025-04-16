package de.aittr.bio_marketplace.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "image")
@Schema(description = "Class that describes a ProductImage entity")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Image unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "url", nullable = false)
    @NotBlank(message = "Image URL cannot be empty")
    @Schema(description = "Image URL", example = "https://bucket.digitalocean.com/images/product1-image1.jpg")
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Schema(description = "Product associated with this image")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @NotNull(message = "Seller cannot be null")
    @Schema(description = "Seller associated with this image")
    private Seller seller;

    // Constructors
    public ProductImage() {
    }

    public ProductImage(Long id, String url, Product product, Seller seller) {
        this.id = id;
        this.url = url;
        this.product = product;
        this.seller = seller;
    }

    // Getters and setters
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(url, that.url) &&
                Objects.equals(product, that.product) &&
                Objects.equals(seller, that.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, product, seller);
    }
}