package de.aittr.bio_marketplace.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import de.aittr.bio_marketplace.domain.entity.QProduct;
import org.apache.commons.lang3.StringUtils;

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

    public ProductQueryPredicateBuilder byNameOrDescription(String search) {
        // Search by title or description
        if (StringUtils.isNoneBlank(search)) {
            String searchPattern = "%" + search.toLowerCase() + "%";
            this.predicate
                    .and(this.product.title.likeIgnoreCase(searchPattern)
                            .or(this.product.description.likeIgnoreCase(searchPattern)));
        }
        return this;
    }

    public ProductQueryPredicateBuilder andStatusActive() {
        // Filter by active status
        this.predicate
                .and(this.product.status.equalsIgnoreCase("active"));
        return this;
    }

    public ProductQueryPredicateBuilder byCategoryId(Long categoryId) {
        // Filter by category
        if (categoryId != null) {
            this.predicate.and(this.product.categoryId.eq(categoryId));
        }

        return this;
    }

    public Predicate build() {
        return this.predicate;
    }
}
