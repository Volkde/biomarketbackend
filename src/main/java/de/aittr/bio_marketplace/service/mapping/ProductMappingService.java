package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMappingService {

    ProductDto mapEntityToDto(Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "active")
    Product mapDtoToEntity(ProductDto dto);

}
