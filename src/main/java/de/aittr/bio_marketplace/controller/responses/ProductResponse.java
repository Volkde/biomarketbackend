package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.ProductDto;

public class ProductResponse {

    private ProductDto product;

    public ProductResponse(ProductDto product) {
        this.product = product;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }
}
