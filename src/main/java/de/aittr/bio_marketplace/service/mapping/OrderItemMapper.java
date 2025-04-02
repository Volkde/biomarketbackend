package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.OrderItemDto;
import de.aittr.bio_marketplace.domain.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.aittr.bio_marketplace.domain.dto.OrderItemDto;
import de.aittr.bio_marketplace.domain.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    OrderItemDto toDto(OrderItem orderItem);

    //обратное преобразование
    @Mapping(source = "productId", target = "product.id")
    OrderItem toEntity(OrderItemDto dto);
}