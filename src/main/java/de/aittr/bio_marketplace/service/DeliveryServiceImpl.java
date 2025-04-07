package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.DeliveryDto;
import de.aittr.bio_marketplace.domain.entity.Delivery;
import de.aittr.bio_marketplace.domain.entity.Order;
import de.aittr.bio_marketplace.repository.DeliveryRepository;
import de.aittr.bio_marketplace.repository.OrderRepository;
import de.aittr.bio_marketplace.service.interfaces.DeliveryService;
import de.aittr.bio_marketplace.service.mapping.DeliveryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final DeliveryMapper deliveryMapper;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository,
                               OrderRepository orderRepository,
                               DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.deliveryMapper = deliveryMapper;
    }

    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.toEntity(deliveryDto);


        if (delivery.getOrder() == null || delivery.getOrder().getId() == null) {
            throw new RuntimeException("Delivery must contain an existing order id");
        }
        Long orderId = delivery.getOrder().getId();
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + orderId));


        //existingOrder.setDelivery(delivery);
        delivery.setOrder(existingOrder);

        Delivery saved = deliveryRepository.save(delivery);
        return deliveryMapper.toDto(saved);
    }

    @Override
    public DeliveryDto getById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found, id=" + id));
        return deliveryMapper.toDto(delivery);
    }

    @Override
    public List<DeliveryDto> getAll() {
        return deliveryRepository.findAll()
                .stream()
                .map(deliveryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryDto update(DeliveryDto deliveryDto) {
        if (deliveryDto.getId() == null) {
            throw new RuntimeException("Delivery id must not be null for update");
        }

        Delivery existing = deliveryRepository.findById(deliveryDto.getId())
                .orElseThrow(() -> new RuntimeException("Delivery not found, id=" + deliveryDto.getId()));

        existing.setAddress(deliveryDto.getAddress());
        existing.setStatus(deliveryDto.getStatus());
        existing.setDeliveryDate(deliveryDto.getDeliveryDate());


        Delivery saved = deliveryRepository.save(existing);
        return deliveryMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!deliveryRepository.existsById(id)) {
            throw new RuntimeException("Delivery not found, id=" + id);
        }
        deliveryRepository.deleteById(id);
    }
}
