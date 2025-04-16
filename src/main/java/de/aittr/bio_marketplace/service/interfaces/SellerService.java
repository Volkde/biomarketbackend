package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.controller.responses.SellerResponse;
import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.entity.Seller;

import java.util.List;

public interface SellerService {

    SellerResponse saveSeller(SellerDto seller, Long user_id);

    List<SellerDto> getAllActiveSellers();

    SellerDto getById(Long id);

    SellerDto update(SellerDto seller);

    SellerDto changeUser(Long user_id, Long seller_id);

    UserDto getUserBySellerId(Long id);

    Seller getActiveSellersEntityById(Long id);

    SellerDto deleteById(Long id);

    SellerDto deactivateById(Long id);

    SellerDto deleteByStoreName(String storeName);

    SellerDto deactivateByStoreName(String storeName);
}
