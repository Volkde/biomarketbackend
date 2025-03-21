package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // --- FIELDS ---

    private final ProductRepository repository;
//    private final ProductMappingService mappingService;

    // --- CONSTRUCTOR ---

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    // --- METHODS ---

    // --- Create ---


    @Override
    public Product save(Product product) {

//        try {
//            Product entity = mappingService.mapDtoToEntity(dto);
//            entity = repository.save(entity);
//            return mappingService.mapEntityToDto(entity);
//        } catch (Exception e) {
//            throw new ProductValidationException(e);
//        }

        return repository.save(product);

    }


    // --- Read ---

    // Returns all active products
    @Override
    public List<Product> getAllActiveProducts() {
        return repository.findAll()
                .stream()
//                .filter(Product::isActive)
//                .map(mappingService::mapEntityToDto)
                .toList();
    }

    // Returns active product by id
    public Product getActiveProductEntityById(Long id) {
        return repository.findById(id)
//                .filter(Product::isActive)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // --- Delete ---

    // Deletes product by id

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

/* TODO:
- activate filter when field active is ready;
- activate mapping when DTO and mapping are ready;
- write function getById when ProductDto class is ready;
 */