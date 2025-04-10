package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.ReviewDto;

public class ReviewResponse {
    private ReviewDto review;

    public ReviewResponse(ReviewDto review) {
        this.review = review;
    }

    public ReviewDto getReview() {
        return review;
    }

    public void setReview(ReviewDto review) {
        this.review = review;
    }
}

