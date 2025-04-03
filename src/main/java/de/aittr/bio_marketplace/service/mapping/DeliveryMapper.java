package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.DeliveryDto;
import de.aittr.bio_marketplace.domain.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
//@Mapping(source = "order", target = "order")
public interface DeliveryMapper {

    DeliveryDto toDto(Delivery delivery);

    @Mapping(target = "order", ignore = true)
    Delivery toEntity(DeliveryDto dto);
}