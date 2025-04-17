package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.ProductImage;
import de.aittr.bio_marketplace.domain.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long productId);

    List<ProductImage> findBySellerAndProductIsNull(Seller seller);

    void deleteByProductId(Long productId);

    void deleteBySellerAndProductIsNull(Seller seller);
}