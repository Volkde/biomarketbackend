package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}