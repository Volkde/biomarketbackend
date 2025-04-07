package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.entity.Seller;

import java.util.List;

public interface SellerService {

    SellerDto saveSeller(SellerDto seller);

    List<SellerDto> getAllActiveSellers();

    SellerDto getById(Long id);

    SellerDto update(SellerDto seller);

    UserDto getUserBySellerId(Long id);

    Seller getActiveSellersEntityById(Long id);

    SellerDto deleteById(Long id);

    SellerDto deleteByStoreName(String storeName);
}
