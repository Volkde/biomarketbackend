package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.AddressDto;

import java.util.List;

public interface AddressService {

    AddressDto save(AddressDto address);

    List<AddressDto> getAll();

    AddressDto getAddressById(Long id);

    List<AddressDto>getAllAddressesByUserId(Long userId);

    List<AddressDto>getAllAddressesBySellerId(Long sellerId);

    void update(AddressDto user);

    void deleteById(Long id);

    void deleteAll();

}
