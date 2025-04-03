package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.domain.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AddressMapper {

    AddressDto mapEntityToDto(Address entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "seller", ignore = true)
    Address mapDtoToEntity(AddressDto dto);

}
