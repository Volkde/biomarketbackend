package de.aittr.bio_marketplace.domain.dto;

import java.util.Objects;

public class CartItemDto {

    private Long productId;
    private String productTitle;
    private int quantity;

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String productTitle, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public int getQuantity() {
        return quantity;
    }


    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItemDto that = (CartItemDto) o;
        return Objects.equals(productId, that.productId)
                && Objects.equals(productTitle, that.productTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productTitle);
    }

    @Override
    public String toString() {
        return "CartItemDto{" +
                "productId=" + productId +
                ", productTitle='" + productTitle + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
