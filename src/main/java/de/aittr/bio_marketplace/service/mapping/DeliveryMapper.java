package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.DeliveryDto;
import de.aittr.bio_marketplace.domain.entity.Delivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryDto toDto(Delivery delivery);
    Delivery toEntity(DeliveryDto dto);
}