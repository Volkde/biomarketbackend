package de.aittr.bio_marketplace.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date")
    private LocalDateTime review_date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public Review() {
    }

    public Review(Seller seller, Product product, LocalDateTime review_date, String comment, BigDecimal rating, Long id) {
        this.seller = seller;
        this.product = product;
        this.review_date = review_date;
        this.comment = comment;
        this.rating = rating;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReview_date() {
        return review_date;
    }

    public void setReview_date(LocalDateTime review_date) {
        this.review_date = review_date;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) && Objects.equals(rating, review.rating) && Objects.equals(comment, review.comment) && Objects.equals(review_date, review.review_date) && Objects.equals(product, review.product) && Objects.equals(seller, review.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, comment, review_date, product, seller);
    }

    @Override
    public String toString() {
        return String.format(" Review: ID - %d, Rating - %s, Comment - %s, Review_date - %s, product - %s,seller - %s.",
                id, rating, comment, review_date, product, seller);
    }
}
