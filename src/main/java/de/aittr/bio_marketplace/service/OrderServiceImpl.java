package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.*;
import de.aittr.bio_marketplace.exception_handling.exceptions.UserNotFoundException;
import de.aittr.bio_marketplace.repository.CartRepository;
import de.aittr.bio_marketplace.repository.OrderRepository;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository,
                            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Order> getOrdersForUser(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return orderRepository.findByUser(user);
    }

    @Override
    public Order createOrder(Order order, String email) {
        //1 user
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        order.setUser(user);
        order.setDateCreated(LocalDateTime.now());

        //2 обраб. OrderItem
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            if (item.getProduct() == null || item.getProduct().getId() == null) {
                throw new IllegalArgumentException("OrderItem must contain productId");
            }
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Product with id " + item.getProduct().getId() + " not found"));

            item.setProduct(product);
            item.setOrder(order);

            BigDecimal itemPrice = product.getPrice().multiply(
                    BigDecimal.valueOf(item.getQuantity())
            );
            item.setPrice(itemPrice);

            total = total.add(itemPrice);
        }
        order.setTotalPrice(total);

        //3 delivery — если приходит из orderDto
        if (order.getDelivery() != null) {
            Delivery del = order.getDelivery();
            del.setOrder(order);
            order.setDelivery(del);
        }

        //4 сохраняем
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderByIdAndUser(Long id, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return orderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Order not found or doesn't belong to user"));
    }

    @Override
    public void deleteOrder(Long id, String email) {
        Order order = getOrderByIdAndUser(id, email);
        orderRepository.delete(order);
    }

    /**
     * Создать заказ на основе содержимого корзины (Cart).
     * После создания — очистить корзину.
     */
    @Override
    @Transactional
    public Order createOrderFromCart(String email) {
        // 1. Найдем пользователя
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        // 2. Проверим корзину
        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty, cannot create an order");
        }

        // 3. Создадим пустой заказ
        Order order = new Order();
        order.setUser(user);
        order.setDateCreated(LocalDateTime.now());
        order.setStatus(false); // например, false = "in progress" / draft

        BigDecimal total = BigDecimal.ZERO;

        // 4. Преобразуем CartItem -> OrderItem
        for (CartItem ci : cart.getItems()) {
            Product product = ci.getProduct();
            OrderItem oi = new OrderItem();
            oi.setProduct(product);
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(product.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
            oi.setOrder(order);

            order.getItems().add(oi);

            total = total.add(oi.getPrice());
        }
        order.setTotalPrice(total);

        // 5. Сохраняем заказ
        Order saved = orderRepository.save(order);

        // 6. Очищаем корзину
        cart.clear();
        cartRepository.save(cart);

        return saved;
    }
}
