package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.CategoryDto;
import de.aittr.bio_marketplace.domain.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto mapEntityToDto(Category entity);

    @Mapping(target = "id", ignore = true)
    Category mapDtoToEntity(CategoryDto dto);
}