package de.aittr.bio_marketplace.domain.dto;

import de.aittr.bio_marketplace.domain.entity.User;

import java.math.BigDecimal;
import java.util.Objects;

public class SellerDto {

    private Long id;
    private String storeName;
    private String storeDescription;
    private String storeLogo;
    private BigDecimal rating;

    public Long getId() {
        return id;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SellerDto sellerDto = (SellerDto) o;
        return Objects.equals(id, sellerDto.id) && Objects.equals(storeName, sellerDto.storeName) && Objects.equals(storeDescription, sellerDto.storeDescription) && Objects.equals(storeLogo, sellerDto.storeLogo) && Objects.equals(rating, sellerDto.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeName, storeDescription, storeLogo, rating);
    }

    @Override
    public String toString() {
        return String.format(" Seller: ID - %d, Store name - %s, Store description - %s, Store logo - %s, rating -%s.", id, storeName, storeDescription, storeLogo, rating);
    }
}
