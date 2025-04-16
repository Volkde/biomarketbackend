package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.CartDto;
import de.aittr.bio_marketplace.domain.dto.CartItemDto;
import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartMapper {

    @Mapping(target = "items", source = "items", qualifiedByName = "mapCartItemsToDto")
    CartDto mapEntityToDto(Cart entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", source = "items", qualifiedByName = "mapCartItemsToEntity")
    Cart mapDtoToEntity(CartDto dto);

    @Named("mapCartItemsToDto")
    List<CartItemDto> mapCartItemsToDto(List<CartItem> items);

    @Named("mapCartItemsToEntity")
    List<CartItem> mapCartItemsToEntity(List<CartItemDto> items);

    @Mapping(target = "product", source = "product")
    CartItemDto mapCartItemToDto(CartItem item);

    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", source = "product")
    CartItem mapCartItemDtoToEntity(CartItemDto dto);
}