package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = CartMapper.class)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "sellerId", source = "seller.id"),
    })
    UserDto mapEntityToDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    User mapDtoToEntity(UserDto dto);
}
