package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.WishlistDto;
import de.aittr.bio_marketplace.domain.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    WishlistDto mapEntityToDto(Wishlist entity);

    @Mapping(target = "id", ignore = true)
    Wishlist mapDtoToEntity(WishlistDto dto);
}