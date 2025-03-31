package de.aittr.bio_marketplace.service.mapping;


import de.aittr.bio_marketplace.domain.dto.CartDto;
import de.aittr.bio_marketplace.domain.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartMapper {

    CartDto mapEntityToDto(Cart entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "allActiveProducts", ignore = true)
    Cart mapDtoToEntity(CartDto dto);
}
