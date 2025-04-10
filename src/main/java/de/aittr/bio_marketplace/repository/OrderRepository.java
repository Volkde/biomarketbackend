package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.Order;
import de.aittr.bio_marketplace.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findBySellerId(Long sellerId);

    List<Order> findByBuyerId(Long buyerId);

    Optional<Order> findByIdAndSellerId(Long id, Long sellerId);

    Optional<Order> findByIdAndBuyerId(Long id, Long buyerId);

    @Query("SELECT o FROM Order o WHERE o.sellerId = :userId OR o.buyerId = :userId")
    List<Order> findByUserId(Long userId);
}
