package de.aittr.bio_marketplace.repository;

import com.querydsl.core.types.Predicate;
import de.aittr.bio_marketplace.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    @Query("select p from Product p where lower(p.status) = lower(?1)")
    List<Product> findAllActive(String status, Predicate predicate);
}