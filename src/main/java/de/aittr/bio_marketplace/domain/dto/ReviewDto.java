package de.aittr.bio_marketplace.domain.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ReviewDto {

    private Long id;
    private BigDecimal rating;
    private String comment;
    private LocalDateTime review_date;

    public LocalDateTime getReview_date() {
        return review_date;
    }

    public void setReview_date(LocalDateTime review_date) {
        this.review_date = review_date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return Objects.equals(id, reviewDto.id) && Objects.equals(rating, reviewDto.rating) && Objects.equals(comment, reviewDto.comment) && Objects.equals(review_date, reviewDto.review_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, comment, review_date);
    }

    @Override
    public String toString() {
        return String.format(" Review: ID - %d, Rating - %s, Comment - %s, Review_date - %s.",
                id, rating, comment, review_date);
    }
}
