package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.Order;
import de.aittr.bio_marketplace.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    //поиск заказа по id и пользователю
    Optional<Order> findByIdAndUser(Long id, User user);
}
