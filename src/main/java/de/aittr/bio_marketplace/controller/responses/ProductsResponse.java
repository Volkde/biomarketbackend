package de.aittr.bio_marketplace.controller.responses;

import de.aittr.bio_marketplace.domain.dto.ProductDto;

import java.util.List;

public class ProductsResponse {

    private List<ProductDto> products;

    public ProductsResponse(List<ProductDto> products) {
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}