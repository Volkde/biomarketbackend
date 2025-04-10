package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.AddressDto;

import java.util.List;

public interface AddressService {

    AddressDto saveUserAddress(AddressDto address, Long user_id);

    AddressDto saveSellerAddress(AddressDto address, Long seller_id);

    List<AddressDto> getAll();

    AddressDto getAddressById(Long id);

    List<AddressDto>getAllAddressesByUserId(Long userId);

    List<AddressDto>getAllAddressesBySellerId(Long sellerId);

    AddressDto update(AddressDto user);

    AddressDto deleteById(Long id);

    void deleteAll();

}
