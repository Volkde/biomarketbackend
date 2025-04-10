package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
