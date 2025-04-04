package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.entity.Seller;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerDto mapEntityToDto(Seller entity);

    Seller mapDtoToEntity(SellerDto dto);

}
