package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.DeliveryDto;

import java.util.List;

public interface DeliveryService {

    DeliveryDto createDelivery(DeliveryDto deliveryDto);

    DeliveryDto getById(Long id);

    List<DeliveryDto> getAll();

    DeliveryDto update(DeliveryDto deliveryDto);

    void deleteById(Long id);
}
