package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.service.RoleToStringConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {CartMapper.class, RoleToStringConverter.class})
public interface RegisterUserMapper {

    @Mapping(source = "username", target = "userName")
    RegisterUserResponseDto mapEntityToRegisterResponseDto(User entity);

    @Mapping(target = "username", source = "userName")
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "sellers", ignore = true)
    @Mapping(target = "password", source = "password")
    @Mapping(target = "isActive", constant = "true")
    User mapRegisterDtoToEntity(RegisterUserDto registerDto);
}

