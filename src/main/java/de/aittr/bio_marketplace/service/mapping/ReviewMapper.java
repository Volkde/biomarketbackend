package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.ReviewDto;
import de.aittr.bio_marketplace.domain.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto mapEntityToDto(Review entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "seller", ignore = true)
    Review mapDtoToEntity(ReviewDto dto);

}
