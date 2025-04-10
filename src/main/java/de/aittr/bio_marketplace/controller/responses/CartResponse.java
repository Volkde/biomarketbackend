package de.aittr.bio_marketplace.controller.responses;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private CartData cart;

    public CartResponse(CartData cart) {
        this.cart = cart;
    }

    public CartData getCart() {
        return cart;
    }

    public void setCart(CartData cart) {
        this.cart = cart;
    }

    public static class CartData {
        private Long cartId;
        private Long userId;
        private List<CartItemResponse> items;
        private BigDecimal totalCartPrice;

        public CartData(Long cartId, Long userId, List<CartItemResponse> items, BigDecimal totalCartPrice) {
            this.cartId = cartId;
            this.userId = userId;
            this.items = items;
            this.totalCartPrice = totalCartPrice;
        }

        public Long getCartId() {
            return cartId;
        }

        public void setCartId(Long cartId) {
            this.cartId = cartId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public List<CartItemResponse> getItems() {
            return items;
        }

        public void setItems(List<CartItemResponse> items) {
            this.items = items;
        }

        public BigDecimal getTotalCartPrice() {
            return totalCartPrice;
        }

        public void setTotalCartPrice(BigDecimal totalCartPrice) {
            this.totalCartPrice = totalCartPrice;
        }
    }

    public static class CartItemResponse {
        private Long productId;
        private String title;
        private String image;
        private BigDecimal quantity;
        private String unitOfMeasure;
        private BigDecimal totalItemPrice;

        public CartItemResponse(Long productId, String title, String image, BigDecimal quantity,
                                String unitOfMeasure, BigDecimal totalItemPrice) {
            this.productId = productId;
            this.title = title;
            this.image = image;
            this.quantity = quantity;
            this.unitOfMeasure = unitOfMeasure;
            this.totalItemPrice = totalItemPrice;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }

        public String getUnitOfMeasure() {
            return unitOfMeasure;
        }

        public void setUnitOfMeasure(String unitOfMeasure) {
            this.unitOfMeasure = unitOfMeasure;
        }

        public BigDecimal getTotalItemPrice() {
            return totalItemPrice;
        }

        public void setTotalItemPrice(BigDecimal totalItemPrice) {
            this.totalItemPrice = totalItemPrice;
        }
    }
}