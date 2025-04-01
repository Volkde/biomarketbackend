package de.aittr.bio_marketplace.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import de.aittr.bio_marketplace.domain.entity.QProduct;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ProductQueryPredicateBuilder {

    private final BooleanBuilder predicate;
    private final QProduct product;

    public ProductQueryPredicateBuilder(BooleanBuilder predicate, QProduct product) {
        this.predicate = predicate;
        this.product = product;
    }

    public static ProductQueryPredicateBuilder builder() {
        final BooleanBuilder builder = new BooleanBuilder();
        final QProduct product = QProduct.product;
        return new ProductQueryPredicateBuilder(builder, product);
    }

    // Search by title or description
    public ProductQueryPredicateBuilder byNameOrDescription(String search) {
        if (StringUtils.isNoneBlank(search)) {
            String searchPattern = "%" + search.toLowerCase() + "%";
            this.predicate
                    .and(this.product.title.likeIgnoreCase(searchPattern)
                            .or(this.product.description.likeIgnoreCase(searchPattern)));
        }
        return this;
    }

    // Filter by active status
    public ProductQueryPredicateBuilder andStatusActive() {
        this.predicate
                .and(this.product.status.equalsIgnoreCase("active"));
        return this;
    }

    // Filter by category
    public ProductQueryPredicateBuilder byCategoryId(Long categoryId) {
        if (categoryId != null) {
            this.predicate
                    .and(this.product.categoryId.eq(categoryId));
        }
        return this;
    }

    // Filter by price range
    public ProductQueryPredicateBuilder byPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {

        if (minPrice != null) {
            if (minPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Minimum price cannot be negative: " + minPrice);
            }
            this.predicate.and(this.product.price.goe(minPrice));
        }

        if (maxPrice != null) {
            if (maxPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Maximum price cannot be negative: " + maxPrice);
            }
            this.predicate.and(this.product.price.loe(maxPrice));
        }

        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("Minimum price (" + minPrice + ") cannot be greater than maximum price (" + maxPrice + ")");
        }

        return this;
    }

    // Filter by seller
    public ProductQueryPredicateBuilder bySellerId(Long sellerId) {
        if (sellerId != null) {
            this.predicate
                    .and(this.product.sellerId.eq(sellerId));
        }
        return this;
    }

    public Predicate build() {
        return this.predicate;
    }
}
