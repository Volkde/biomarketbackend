package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.service.RoleToStringConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {CartMappingService.class, RoleToStringConverter.class})
public interface RegisterUserMappingService {
    RegisterUserResponseDto mapEntityToRegisterResponseDto(User entity);

    @Mapping(target = "username", source = "userName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password")
    @Mapping(target = "active", constant = "true")

    User mapRegisterDtoToEntity(RegisterUserDto registerDto);


}

