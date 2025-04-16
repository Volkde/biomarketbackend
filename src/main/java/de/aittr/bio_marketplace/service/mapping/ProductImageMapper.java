package de.aittr.bio_marketplace.service.mapping;

import de.aittr.bio_marketplace.domain.dto.ProductImageDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.ProductImage;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.repository.ProductRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
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
    ProductImage toEntity(ProductImageDto dto, @Context ProductRepository productRepository, @Context Seller seller);

    List<ProductImageDto> toDtoList(List<ProductImage> entities);

    List<ProductImage> toEntityList(List<ProductImageDto> dtos, @Context ProductRepository productRepository, @Context Seller seller);

    @AfterMapping
    default void afterDtoToEntityMapping(ProductImageDto dto, @MappingTarget ProductImage image,
                                         @Context ProductRepository productRepository,
                                         @Context Seller seller) {
        // Устанавливаем product
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));
            image.setProduct(product);
        }

        // Устанавливаем seller
        if (seller == null) {
            throw new IllegalArgumentException("Seller cannot be null when mapping ProductImage");
        }
        image.setSeller(seller);
    }
}