//package de.aittr.bio_marketplace.service;
//
//import de.aittr.bio_marketplace.domain.entity.Delivery;
//import de.aittr.bio_marketplace.domain.entity.Order;
//import de.aittr.bio_marketplace.domain.entity.OrderItem;
//import de.aittr.bio_marketplace.domain.entity.Product;
//import de.aittr.bio_marketplace.domain.entity.User;
//import de.aittr.bio_marketplace.exception_handling.exceptions.UserNotFoundException;
//import de.aittr.bio_marketplace.repository.OrderRepository;
//import de.aittr.bio_marketplace.repository.ProductRepository;
//import de.aittr.bio_marketplace.repository.UserRepository;
//import de.aittr.bio_marketplace.service.interfaces.OrderService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@Transactional
//public class OrderServiceImpl implements OrderService {
//
//    private final OrderRepository orderRepository;
//    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
//
//    public OrderServiceImpl(OrderRepository orderRepository,
//                            UserRepository userRepository,
//                            ProductRepository productRepository) {
//        this.orderRepository = orderRepository;
//        this.userRepository = userRepository;
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    public List<Order> getOrdersForUser(String email) {
//        User user = userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException(0L));
//        return orderRepository.findByUser(user);
//    }
//
//    @Override
//    public Order createOrder(Order order, String email) {
//        //1 user
//        User user = userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException(0L));
//        order.setUser(user);
//        order.setDateCreated(LocalDateTime.now());
//
//        //2 обраб. OrderItem
//        BigDecimal total = BigDecimal.ZERO;
//        for (OrderItem item : order.getItems()) {
//            if (item.getProduct() == null || item.getProduct().getId() == null) {
//                throw new IllegalArgumentException("OrderItem must contain productId");
//            }
//            Product product = productRepository.findById(item.getProduct().getId())
//                    .orElseThrow(() -> new IllegalArgumentException(
//                            "Product with id " + item.getProduct().getId() + " not found"));
//
//            item.setProduct(product);
//            item.setOrder(order);
//
//            BigDecimal itemPrice = product.getPrice().multiply(
//                    BigDecimal.valueOf(item.getQuantity())
//            );
//            item.setPrice(itemPrice);
//
//            total = total.add(itemPrice);
//        }
//        order.setTotalPrice(total);
//
//        //3 delivery
//        if (order.getDelivery() != null) {
//            Delivery del = order.getDelivery();
//            //двухсторонняя связь
//            del.setOrder(order);
//            order.setDelivery(del);
//        }
//
//        //4 сохраняем
//        return orderRepository.save(order);
//    }
//
//    @Override
//    public Order getOrderByIdAndUser(Long id, String email) {
//        User user = userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException(0L));
//        return orderRepository.findByIdAndUser(id, user)
//                .orElseThrow(() -> new RuntimeException("Order not found or doesn't belong to user"));
//    }
//
//    @Override
//    public void deleteOrder(Long id, String email) {
//        Order order = getOrderByIdAndUser(id, email);
//        orderRepository.delete(order);
//    }
//}
