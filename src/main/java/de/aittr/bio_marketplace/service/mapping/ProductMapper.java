package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.ProductImage;
import de.aittr.bio_marketplace.domain.entity.Seller;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "sellerId", expression = "java(entity.getSellerId())")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "images", expression = "java(entity.getImages().stream().map(de.aittr.bio_marketplace.domain.entity.ProductImage::getUrl).toList())")
    ProductDto mapEntityToDto(Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(de.aittr.bio_marketplace.domain.entity.ProductStatus.ACTIVE)")
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product mapDtoToEntity(ProductDto dto);

    List<ProductDto> mapEntityToDtoList(List<Product> entities);

    @AfterMapping
    default void afterDtoToEntityMapping(ProductDto dto, @MappingTarget Product product) {
        // Устанавливаем images
        List<ProductImage> images = new ArrayList<>();
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            for (String imageUrl : dto.getImages()) {
                ProductImage image = new ProductImage();
                image.setUrl(imageUrl);
                image.setProduct(product);
                // Seller должен быть установлен в сервисе, поэтому здесь его не устанавливаем
                images.add(image);
            }
        }
        product.setImages(images); // Полностью заменяем список, даже если он пустой
    }
}