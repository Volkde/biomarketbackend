package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.ProductImageDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.ProductImage;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.repository.ProductRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "sellerId", source = "seller.id")
    ProductImageDto toDto(ProductImage entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "seller", ignore = true)
    ProductImage toEntity(ProductImageDto dto);

    List<ProductImageDto> toDtoList(List<ProductImage> entities);

    List<ProductImage> toEntityList(List<ProductImageDto> dtos);

    @AfterMapping
    default void afterDtoToEntityMapping(ProductImageDto dto, @MappingTarget ProductImage image) {
        // Устанавливаем product только если productId не null
        if (dto.getProductId() != null) {
            throw new UnsupportedOperationException("Product assignment not implemented yet");
        }
        // Seller устанавливается через sellerId в основном маппинге (не здесь)
    }
}