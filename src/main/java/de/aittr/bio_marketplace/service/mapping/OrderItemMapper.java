package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.OrderItemDto;
import de.aittr.bio_marketplace.domain.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDto dto);
}