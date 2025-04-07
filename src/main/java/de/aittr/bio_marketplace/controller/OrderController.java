package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.domain.dto.OrderDto;
import de.aittr.bio_marketplace.domain.entity.Order;
import de.aittr.bio_marketplace.service.interfaces.OrderService;
import de.aittr.bio_marketplace.service.mapping.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final OrderMapper orderMapper;


    public OrderController(OrderService orderService,
                           OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrdersForUser(Principal principal) {
        List<Order> orders = orderService.getOrdersForUser(principal.getName());
        List<OrderDto> dtos = orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, Principal principal) {
        logger.info("Получен OrderDto: {}", orderDto);

        //Логируем OrderItems
        if (orderDto.getItems() != null) {
            orderDto.getItems().forEach(item -> {
                logger.info("Элемент заказа: productId={}, quantity={}",
                        item.getProductId(), item.getQuantity());
            });
        }

        //dto -> entity
        Order order = orderMapper.toEntity(orderDto);

        //сохр.
        Order saved = orderService.createOrder(order, principal.getName());
        OrderDto savedDto = orderMapper.toDto(saved);

        logger.info("Сохранённый OrderDto: {}", savedDto);
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id, Principal principal) {
        Order order = orderService.getOrderByIdAndUser(id, principal.getName());
        return ResponseEntity.ok(orderMapper.toDto(order));
    }


    @PostMapping("/from-cart")
    public ResponseEntity<OrderDto> createOrderFromCart(Principal principal) {
        //Вызываем сервисный метод
        Order order = orderService.createOrderFromCart(principal.getName());
        OrderDto savedDto = orderMapper.toDto(order);
        return ResponseEntity.ok(savedDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id, Principal principal) {
        orderService.deleteOrder(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
