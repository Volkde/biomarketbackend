package de.aittr.bio_marketplace.service.interfaces;
import de.aittr.bio_marketplace.domain.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto saveByProduct(ReviewDto reviewDto, Long productId);

    ReviewDto saveBySeller(ReviewDto reviewDto, Long sellerId);

    List<ReviewDto> getAll();

    List<ReviewDto>getAllReviewsByProductId(Long productId);

    List<ReviewDto>getAllReviewsBySellerId(Long sellerId);

    ReviewDto getReviewsById(Long id);

    ReviewDto updateById(ReviewDto user);

    ReviewDto deleteReviewById(Long id);

    void deleteAll();

}
