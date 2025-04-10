package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.OrderDto;
import de.aittr.bio_marketplace.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, DeliveryMapper.class})
public interface OrderMapper {

    OrderDto toDto(Order order);

    @Mapping(target = "items.order", ignore = true)
    Order toEntity(OrderDto orderDto);
}
