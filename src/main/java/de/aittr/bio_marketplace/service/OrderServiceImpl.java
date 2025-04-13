package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.domain.dto.OrderDto;
import de.aittr.bio_marketplace.domain.entity.*;
import de.aittr.bio_marketplace.exception_handling.exceptions.EmptyCartException;
import de.aittr.bio_marketplace.exception_handling.exceptions.SellerAddressNotFoundException;
import de.aittr.bio_marketplace.exception_handling.exceptions.UserNotFoundException;
import de.aittr.bio_marketplace.repository.OrderRepository;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.AddressService;
import de.aittr.bio_marketplace.service.interfaces.CartService;
import de.aittr.bio_marketplace.service.interfaces.OrderService;
import de.aittr.bio_marketplace.service.mapping.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final AddressService addressService;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            CartService cartService,
                            AddressService addressService,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.addressService = addressService;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDto> createOrdersFromCart(AddressDto buyerAddress, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        Long buyerId = user.getId();

        Cart cart = cartService.getCartByUserId(buyerId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cart is empty or not found for user " + email);
        }

        Map<Long, List<CartItem>> itemsBySeller = cart.getItems().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getSeller().getId()));

        List<Order> orders = new ArrayList<>();

        for (Map.Entry<Long, List<CartItem>> entry : itemsBySeller.entrySet()) {
            Long sellerId = entry.getKey();
            List<CartItem> sellerItems = entry.getValue();

            Order order = new Order();
            order.setBuyerId(buyerId);
            order.setSellerId(sellerId);

            AddressDto savedBuyerAddressDto = addressService.saveOrderAddress(buyerAddress);
            if (savedBuyerAddressDto.getId() == null) {
                throw new IllegalStateException("Failed to save buyer address: ID is null");
            }
            Address buyerEntity = addressService.getAddressEntityById(savedBuyerAddressDto.getId());
            if (buyerEntity.getId() == null) {
                throw new IllegalStateException("Buyer address entity has null ID after retrieval");
            }
            System.out.println("Buyer address ID: " + buyerEntity.getId());
            order.setBuyerAddress(buyerEntity);

            List<AddressDto> sellerAddresses = addressService.getAllAddressesBySellerId(sellerId);
            if (sellerAddresses.isEmpty()) {
                throw new SellerAddressNotFoundException("No address found for seller " + sellerId);
            }
            Address sellerEntity = addressService.getAddressEntityById(sellerAddresses.get(0).getId());
            if (sellerEntity.getId() == null) {
                throw new IllegalStateException("Seller address entity has null ID after retrieval");
            }
            System.out.println("Seller address ID: " + sellerEntity.getId());
            order.setSellerAddress(sellerEntity);

            BigDecimal totalPrice = sellerItems.stream()
                    .map(item -> item.getProduct().getPrice().multiply(item.getQuantity()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setTotalPrice(totalPrice);
            order.setStatus(OrderStatus.CREATED);
            order.setDateCreated(LocalDateTime.now());

            Order savedOrder = orderRepository.save(order);
            if (savedOrder.getId() == null) {
                throw new IllegalStateException("Saved order has null ID");
            }
            System.out.println("Saved order ID: " + savedOrder.getId());

            List<OrderItem> orderItems = sellerItems.stream()
                    .map(cartItem -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrder(savedOrder);
                        orderItem.setProductId(cartItem.getProduct().getId());
                        orderItem.setProductName(cartItem.getProduct().getTitle());
                        orderItem.setQuantity(cartItem.getQuantity());
                        orderItem.setUnitPrice(cartItem.getProduct().getPrice());
                        return orderItem;
                    })
                    .collect(Collectors.toList());
            savedOrder.setItems(orderItems);

            orders.add(savedOrder);
        }

        System.out.println("Saving orders with items...");
        List<Order> savedOrders = orderRepository.saveAll(orders);
        System.out.println("Orders saved successfully");
        cartService.clearCart(cart.getId());

        return savedOrders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersForUser(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return orderRepository.findByUserId(user.getId()).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderByIdAndUser(Long id, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        Order order = orderRepository.findByIdAndBuyerId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Order not found or doesn't belong to user"));
        return orderMapper.toDto(order);
    }

    @Override
    public void deleteOrder(Long id, String email) {
        Order order = getOrderByIdAndUser(id, email).toEntity();
        orderRepository.delete(order);
    }
}