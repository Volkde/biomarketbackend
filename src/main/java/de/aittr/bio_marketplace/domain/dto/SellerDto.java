package de.aittr.bio_marketplace.domain.dto;

import de.aittr.bio_marketplace.domain.entity.User;

import java.math.BigDecimal;

public class SellerDto {

    private Long id;
    private String storeName;
    private String storeDescription;
    private String storeLogo;
    private BigDecimal rating;
    private User user;
}
