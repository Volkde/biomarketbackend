package de.aittr.bio_marketplace.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.ProductStatus;
import de.aittr.bio_marketplace.domain.entity.QProduct;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductValidationException;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import de.aittr.bio_marketplace.service.interfaces.ReviewService;
import de.aittr.bio_marketplace.service.mapping.ProductMapper;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // --- FIELDS ---

    private final ProductRepository repository;
    private final ProductMapper mappingService;
    private final ReviewService reviewService;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // --- CONSTRUCTOR ---

    public ProductServiceImpl(ProductRepository repository, ProductMapper productMapper,@Lazy ReviewService reviewService) {
        this.repository = repository;
        this.mappingService = productMapper;
        this.reviewService = reviewService;
    }


    // --- METHODS ---

    // --- Create ---

    @Override
    public ProductDto save(ProductDto dto, Seller seller) {
        try {
            if (dto.getSellerId() != null && !dto.getSellerId().equals(seller.getId())) {
                throw new IllegalArgumentException("Seller ID in DTO does not match provided Seller");
            }
            Product entity = mappingService.mapDtoToEntity(dto);
            entity.setSeller(seller); // Устанавливаем seller перед сохранением
            entity = repository.save(entity);
            return mappingService.mapEntityToDto(entity);
        } catch (Exception e) {
            throw new ProductValidationException(e);
        }
    }

    // --- Read ---

    // Returns all active products
    @Override
    public List<ProductDto> getAllActiveProducts() {
        return repository.findAll()
                .stream()
                .filter(product -> product.getStatus() == ProductStatus.ACTIVE)
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    // Returns active product by id
    @Override
    public ProductDto getById(Long id) {
        return mappingService.mapEntityToDto(getActiveProductEntityById(id));
    }

    // Returns active product entity by id
    @Transactional
    public Product getActiveProductEntityById(Long id) {
        Product product = repository.findById(id)
                .filter(p -> p.getStatus() == ProductStatus.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException(id));
        Hibernate.initialize(product.getSeller()); // Явная загрузка seller в транзакции
        logger.debug("Loaded product with ID: {}, sellerId: {}", id, product.getSeller().getId());
        return product;
    }

    // Returns active products filtered by given parameters
    @Override
    public List<ProductDto> getAllActiveProductsFiltered(
            String search,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Long sellerID,
            Double ratingMin,
            Boolean inStock,
            Boolean discounted,
            String sortBy,
            String sortOrder
    ) {
        Predicate predicate =ProductQueryPredicateBuilder.builder()
                .andStatusActive()
                .byNameOrDescription(search)
                .byCategoryId(categoryId)
                .byPriceRange(minPrice, maxPrice)
                .bySellerId(sellerID)
                .byMinRating(ratingMin)
                .byInStock(inStock)
                .byDiscounted(discounted)
                .build();

        // Creating a sorting
        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(sortBy, sortOrder);

        // Executing a query with a predicate and sorting
        List<Product> products;
        if (orderSpecifier != null) {
            products = (List<Product>) repository.findAll(predicate, orderSpecifier);
        } else {
            products = (List<Product>) repository.findAll(predicate);
        }

        return products.stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    private OrderSpecifier<?> createOrderSpecifier(String sortBy, String sortOrder) {
        if (sortBy == null || sortBy.isBlank()) {
            return null;
        }

        Order direction = "desc".equalsIgnoreCase(sortOrder) ? Order.DESC : Order.ASC;

        QProduct qProduct = QProduct.product;

        return switch (sortBy.toLowerCase()) {
            case "title" -> new OrderSpecifier<>(direction, qProduct.title);
            case "price" -> new OrderSpecifier<>(direction, qProduct.price);
            case "rating" -> new OrderSpecifier<>(direction, qProduct.rating);
            default -> null;
        };
    }

    // --- Update ---

    @Override
    @Transactional
    public ProductDto update(ProductDto product, Seller seller) {
        Long id = product.getId();
        if (id == null) {
            logger.error("Attempted to update a null product");
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        logger.info("Updating product with ID: {}", id);
        Product existentProduct = repository.findById(id)
                .filter(p -> p.getStatus() == ProductStatus.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Product updatedEntity = mappingService.mapDtoToEntity(product);
        updatedEntity.setId(id);
        updatedEntity.setStatus(existentProduct.getStatus());
        updatedEntity.setSeller(seller != null ? seller : existentProduct.getSeller());

        Product updatedProduct = repository.save(updatedEntity);
        logger.info("Successfully updated product with ID: {}", id);

        return mappingService.mapEntityToDto(updatedProduct);
    }

    @Override
    @Transactional
    public ProductDto activateById(Long id) {
        if (id == null) {
            logger.error("Attempted to activate a product with null ID");
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        logger.info("Activating product with ID: {}", id);
        Product existentProduct = repository.findById(id)
//                .filter(p -> p.getStatus() == ProductStatus.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existentProduct.setStatus(ProductStatus.ACTIVE);

        Product activatedProduct = repository.save(existentProduct);
        logger.info("Successfully activated product with ID: {}", id);

        return mappingService.mapEntityToDto(activatedProduct);
    }

    // --- Delete ---

    @Override
    @Transactional
    public ProductDto deactivateById(Long id) {
        if (id == null) {
            logger.error("Attempted to deactivate a product with null ID");
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        logger.info("Deactivating product with ID: {}", id);
        Product existentProduct = repository.findById(id)
                .filter(p -> p.getStatus() == ProductStatus.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existentProduct.setStatus(ProductStatus.DELETED);

        Product deactivatedProduct = repository.save(existentProduct);
        logger.info("Successfully deactivated product with ID: {}", id);

        return mappingService.mapEntityToDto(deactivatedProduct);
    }

    // Deletes product by id
    @Override
    public ProductDto deleteById(Long id) {
        ProductDto productDto = getById(id);
        reviewService.deleteAllReviewsByProductId(id);
        repository.deleteById(id);
        return productDto;
    }

    @Override
    @Transactional
    public void deleteAllBySellerId(Long sellerId) {
        List<Product> products = repository.findAll()
                .stream()
                .filter(product -> product.getSeller() != null
                        && product.getSeller().getId().equals(sellerId))
                .toList();

        if (!products.isEmpty()) {
            repository.deleteAll(products);
        }
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found for seller with ID: " + sellerId);
        }
    }

}