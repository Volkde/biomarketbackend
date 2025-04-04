package de.aittr.bio_marketplace.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.QProduct;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductValidationException;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import de.aittr.bio_marketplace.service.mapping.ProductMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // --- FIELDS ---

    private final ProductRepository repository;
    private final ProductMapper mappingService;

    // --- CONSTRUCTOR ---

    public ProductServiceImpl(ProductRepository repository, ProductMapper mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }


    // --- METHODS ---

    // --- Create ---

    @Override
    public ProductDto save(ProductDto dto) {

        try {
            Product entity = mappingService.mapDtoToEntity(dto);
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
                .filter(product -> "active".equalsIgnoreCase(product.getStatus()))
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
                .filter(product -> "active".equalsIgnoreCase(product.getStatus()))
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
        switch (sortBy.toLowerCase()) {
            case "title":
                return new OrderSpecifier<>(direction, qProduct.title);
            case "price":
                return new OrderSpecifier<>(direction, qProduct.price);
            case "rating":
                return new OrderSpecifier<>(direction, qProduct.rating);
            default:
                return null;
        }
    }

    // --- Delete ---

    // Deletes product by id

    @Override
    public ProductDto deleteById(Long id) {
        ProductDto productDto = getById(id);
        repository.deleteById(id);
        return productDto;
    }
}

/* TODO:
- write function getById when ProductDto class is ready;
 */