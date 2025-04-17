package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.ProductImageDto;

public class ProductImageResponse {

    private ProductImageDto productImage;

    public ProductImageResponse(ProductImageDto productImage) {
        this.productImage = productImage;
    }

    public ProductImageDto getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImageDto productImage) {
        this.productImage = productImage;
    }
}