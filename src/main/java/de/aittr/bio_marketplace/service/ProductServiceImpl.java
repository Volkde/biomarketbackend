package de.aittr.bio_marketplace.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.QProduct;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductValidationException;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import de.aittr.bio_marketplace.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // --- FIELDS ---

    private final ProductRepository repository;
    private final ProductMappingService mappingService;

    // --- CONSTRUCTOR ---

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
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
            BigDecimal maxPrice
    ) {
        Predicate predicate =ProductQueryPredicateBuilder.builder()
                .andStatusActive()
                .byNameOrDescription(search)
                .byCategoryId(categoryId)
                .byPriceRange(minPrice, maxPrice)
                .build();

        List<Product> products = (List<Product>) repository.findAll(predicate);

        return products.stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    // --- Delete ---

    // Deletes product by id

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

/* TODO:
- write function getById when ProductDto class is ready;
 */