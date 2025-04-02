package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.OrderDto;
import de.aittr.bio_marketplace.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, DeliveryMapper.class})
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    //обратное преобразование маппинг userId из DTO в поле id объекта User
    @Mapping(source = "userId", target = "user.id")
    Order toEntity(OrderDto orderDto);
}
