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
import de.aittr.bio_marketplace.service.interfaces.SellerService;
import de.aittr.bio_marketplace.service.mapping.ProductMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // --- FIELDS ---

    private final ProductRepository repository;
    private final ProductMapper mappingService;
    private final SellerService sellerService;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // --- CONSTRUCTOR ---

    public ProductServiceImpl(ProductRepository repository, ProductMapper productMapper, SellerService sellerService) {
        this.repository = repository;
        this.mappingService = productMapper;
        this.sellerService = sellerService;
    }


    // --- METHODS ---

    // --- Create ---

    @Override
    public ProductDto save(ProductDto dto) {

        try {
            Product entity = mappingService.mapDtoToEntity(dto);
            entity.setSeller(sellerService.getActiveSellersEntityById(dto.getSellerId()));
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
    public Product getActiveProductEntityById(Long id) {
        return repository.findById(id)
                .filter(product -> product.getStatus() == ProductStatus.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException(id));
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
    public ProductDto update(ProductDto product) {
        Long id = product.getId();
        if (id == null) {
            logger.error("Attempted to update a null product");
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        logger.info("Updating product with ID: {}", id);
        Product existentProduct = repository.findById(id)
                .filter(p -> p.getStatus() == ProductStatus.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (product.getTitle() != null) {
            existentProduct.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            existentProduct.setDescription(product.getDescription());
        }
        if (product.getImage() != null) {
            existentProduct.setImage(product.getImage());
        }
        if (product.getUnitOfMeasure() != null) {
            existentProduct.setUnitOfMeasure(product.getUnitOfMeasure());
        }
        if (product.getPrice() != null) {
            existentProduct.setPrice(product.getPrice());
        }
        if (product.isDiscounted() != null) {
            existentProduct.setDiscounted(product.isDiscounted());
        }
        if (product.isInStock() != null) {
            existentProduct.setInStock(product.isInStock());
        }
        if (product.getCategoryId() != null) {
            existentProduct.setCategoryId(product.getCategoryId());
        }
        if (product.getSellerId() != null) {
            Seller seller = sellerService.getActiveSellersEntityById(product.getSellerId());
            existentProduct.setSeller(seller);
        }
        if (product.getRating() != null) {
            existentProduct.setRating(product.getRating());
        }

        Product updatedProduct = repository.save(existentProduct);
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
        repository.deleteById(id);
        return productDto;
    }
}