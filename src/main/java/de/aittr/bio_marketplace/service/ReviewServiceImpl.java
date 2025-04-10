package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.ReviewDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.Review;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.exception_handling.exceptions.*;
import de.aittr.bio_marketplace.exception_handling.utils.ReviewValidator;
import de.aittr.bio_marketplace.repository.ReviewRepository;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import de.aittr.bio_marketplace.service.interfaces.ReviewService;
import de.aittr.bio_marketplace.service.interfaces.SellerService;
import de.aittr.bio_marketplace.service.mapping.ReviewMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    private final ReviewMapper mapper;
    private final SellerService sellerService;
    private final ProductService productService;

    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper reviewMapper, SellerService sellerService, ProductService productService) {
        this.repository = repository;
        this.mapper = reviewMapper;
        this.sellerService = sellerService;
        this.productService = productService;
    }


    @Override
    @Transactional
    public ReviewDto saveByProduct(ReviewDto review, Long productId) {

        Review entity = mapper.mapDtoToEntity(review);
        entity.setId(null);
        entity.setReview_date(LocalDateTime.now());
        ReviewValidator.isValidRating(entity.getRating());
        Product product = productService.getActiveProductEntityById(productId);
        entity = repository.save(entity);
        entity.setProduct(product);
        Double rating = getAverageRating(getAllEntityByProduct(productId)).doubleValue();
        product.setRating(rating);
        return mapper.mapEntityToDto(entity);

    }

    @Override
    @Transactional
    public ReviewDto saveBySeller(ReviewDto review, Long sellerId) {
        Review entity = mapper.mapDtoToEntity(review);
        entity.setId(null);
        entity.setReview_date(LocalDateTime.now());
        ReviewValidator.isValidRating(entity.getRating());
        Seller seller = sellerService.getActiveSellersEntityById(sellerId);
        entity = repository.save(entity);
        entity.setSeller(seller);
        BigDecimal rating = getAverageRating(getAllEntityBySeller(sellerId));
        seller.setRating(rating);
        return mapper.mapEntityToDto(entity);
    }

    @Override
    public List<ReviewDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @Override
    public List<ReviewDto> getAllReviewsByProductId(Long productId) {
        return getAllEntityByProduct(productId).stream()
                .map(mapper::mapEntityToDto)
                .toList();
    }

    private List<Review> getAllEntityByProduct(Long productId) {
        try {
            return repository.findAll()
                    .stream()
                    .filter(review -> review.getProduct() != null && review.getProduct().getId().equals(productId))
                    .toList();
        } catch (ReviewByProductNotFoundException e) {
            throw new ReviewByProductNotFoundException(productId);
        }
    }

    @Override
    public List<ReviewDto> getAllReviewsBySellerId(Long sellerId) {
        return getAllEntityBySeller(sellerId).stream()
                .map(mapper::mapEntityToDto)
                .toList();
    }

    private List<Review> getAllEntityBySeller(Long sellerId) {
        try {
            return repository.findAll()
                    .stream()
                    .filter(review -> review.getSeller() != null && review.getSeller().getId().equals(sellerId))
                    .toList();
        } catch (AddressBySellerNotFoundException e) {
            throw new ReviewBySellerNotFoundException(sellerId);
        }
    }

    @Override
    public ReviewDto getReviewsById(Long id) {
        return mapper.mapEntityToDto(getEntityById(id));
    }

    public Review getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Override
    @Transactional
    public ReviewDto updateById(ReviewDto review) {
        Long id = review.getId();
        Review findReview = getEntityById(id);

        if (review.getRating() == null){
            review.setRating(findReview.getRating());
        }
        ReviewValidator.isValidRating(review.getRating());
        findReview.setRating(review.getRating());

        if (review.getComment() == null){
            review.setComment(findReview.getComment());
        }
        findReview.setComment(review.getComment());

        if (review.getReview_date() == null){
            review.setReview_date(findReview.getReview_date());
        }
        changeRating(findReview);
        return mapper.mapEntityToDto(findReview);
    }

    @Override
    @Transactional
    public ReviewDto deleteReviewById(Long id) {
        Review review = getEntityById(id);
        repository.deleteById(id);
        changeRating(review);
        return mapper.mapEntityToDto(review);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    private void changeRating(Review review){

        if(review.getProduct() == null){
            Seller seller = sellerService.getActiveSellersEntityById(review.getSeller().getId());
            List<Review> reviews = getAllEntityBySeller(seller.getId());
            seller.setRating(getAverageRating(reviews));
        }
        if(review.getSeller() == null){
            Product product = productService.getActiveProductEntityById(review.getProduct().getId());

            List<Review> reviews = getAllEntityByProduct(product.getId());
            product.setRating(getAverageRating(reviews).doubleValue());
        }
    }
    private BigDecimal getAverageRating(List<Review> reviews){
        BigDecimal currentRating;
        if (reviews.isEmpty()) {
            currentRating = BigDecimal.ZERO;
        } else {
            BigDecimal sum = reviews.stream()
                    .map(Review::getRating)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal count = BigDecimal.valueOf(reviews.size());

            try {
                currentRating = sum.divide(count, RoundingMode.UNNECESSARY);
            } catch (ArithmeticException e) {
                currentRating = sum.divide(count, 2, RoundingMode.HALF_UP);
            }
        }
        return currentRating;
    }
}
