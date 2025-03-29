package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByTitle(String title);

    List<Product> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndStatus(String titleSearch, String descriptionSearch, String status);

}