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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // --- FIELDS ---

    private final ProductRepository repository;
    private final ProductMapper mappingService;

    // --- CONSTRUCTOR ---

    public ProductServiceImpl(ProductRepository repository, ProductMapper productMapper) {
        this.repository = repository;
        this.mappingService = productMapper;
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
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        Product existentProduct = repository.findById(id)
                .filter(p -> "active".equalsIgnoreCase(p.getStatus()))
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
            existentProduct.setSellerId(product.getSellerId());
        }
        if (product.getRating() != null) {
            existentProduct.setRating(product.getRating());
        }

        Product updatedProduct = repository.save(existentProduct);
        return mappingService.mapEntityToDto(updatedProduct);
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