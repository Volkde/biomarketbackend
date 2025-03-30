package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CartMappingService.class)
public interface UserMappingService {


    UserDto mapEntityToDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    User mapDtoToEntity(UserDto dto);
}
