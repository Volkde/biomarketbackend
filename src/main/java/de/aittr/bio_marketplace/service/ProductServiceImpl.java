package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Product;
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

    @Override
    public List<Product> getAllActiveProducts() {
        return repository.findAll()
                .stream()
//                .filter(Product::isActive)
//                .map(mappingService::mapEntityToDto)
                .toList();
    }
}

/* TODO:
- activate filter when active field is ready;
- activate mapping when DTO and mapping are ready;
 */