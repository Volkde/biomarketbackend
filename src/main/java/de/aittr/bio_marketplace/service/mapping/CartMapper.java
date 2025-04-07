package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.CartDto;
import de.aittr.bio_marketplace.domain.dto.CartItemDto;
import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.CartItem;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CartMapper {

    @Mapping(target = "items", expression = "java(mapCartItems(entity.getItems()))")
    public abstract CartDto mapEntityToDto(Cart entity);

    @InheritInverseConfiguration
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    public abstract Cart mapDtoToEntity(CartDto dto);

    protected List<CartItemDto> mapCartItems(List<CartItem> cartItems) {
        if (cartItems == null) {
            return List.of();
        }
        return cartItems.stream()
                .map(this::mapCartItemToDto)
                .collect(Collectors.toList());
    }

    protected CartItemDto mapCartItemToDto(CartItem ci) {
        CartItemDto dto = new CartItemDto();
        dto.setProductId(ci.getProduct().getId());
        dto.setProductTitle(ci.getProduct().getTitle());
        dto.setQuantity(ci.getQuantity());
        return dto;
    }
}
