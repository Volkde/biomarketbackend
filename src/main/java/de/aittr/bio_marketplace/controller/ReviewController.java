package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.responses.ReviewResponse;
import de.aittr.bio_marketplace.domain.dto.ReviewDto;
import de.aittr.bio_marketplace.service.interfaces.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Review controller", description = "Controller for various operations with Review")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @Operation(
            summary = "Save review by product id",
            description = "Saving new review with given parameters by product id"
    )
    @PostMapping("/save-by-product/{product_id}")
    public ReviewResponse saveProductReview(
            @RequestBody
            ReviewDto review,
            @PathVariable Long product_id) {
        return new ReviewResponse(service.saveByProduct(review, product_id));
    }

    @Operation(
            summary = "Save review by seller id",
            description = "Saving new review with given parameters by seller id"
    )
    @PostMapping("/save-by-seller/{seller_id}")
    public ReviewResponse saveSellerReview(
            @RequestBody ReviewDto review,
            @PathVariable Long seller_id) {
        return new ReviewResponse(service.saveBySeller(review, seller_id));
    }

    @GetMapping
    @Operation(
            summary = "Get all reviews",
            description = "Getting all reviews that exist in the database"
    )
    public List<ReviewDto> getAllReviews() {
        return service.getAll();
    }

    @GetMapping("/get-all-by-product/{product_id}")
    @Operation(
            summary = "Get all reviews by product id",
            description = "Getting all reviews from database by product id"
    )
    public List<ReviewDto> getAllReviewsByProductId(@PathVariable Long product_id) {
        return service.getAllReviewsByProductId(product_id);
    }

    @GetMapping("/get-all-by-seller/{seller_id}")
    @Operation(
            summary = "Get all reviews by seller id",
            description = "Getting all reviews from database by seller id"
    )
    public List<ReviewDto> getAllReviewsBySellerId(@PathVariable Long seller_id) {
        return service.getAllReviewsBySellerId(seller_id);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get review by id",
            description = "Getting review from database by id"
    )
    public ReviewResponse getReviewById(
            @PathVariable
            @Parameter(description = "Reviews unique identifier")
            Long id
    ) {
        return new ReviewResponse(service.getReviewsById(id));
    }


    @Operation(
            summary = "Update review by id",
            description = "Updating review from database by id"
    )
    @PutMapping
    public ReviewResponse update(@RequestBody ReviewDto review) {
        return new ReviewResponse(service.updateById(review));
    }

    @Operation(
            summary = "Delete review by id",
            description = "Deletion review from database by id"
    )
    @DeleteMapping("/{id}")
    public ReviewResponse deleteById(@PathVariable Long id) {
        return new ReviewResponse(service.deleteReviewById(id));
    }

    @Operation(
            summary = "Delete all reviews without resetting the rating",
            description = "Deletion all reviews without resetting the rating(seller, product) from database by id"
    )
    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }

}
