package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.domain.entity.Address;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exception_handling.exceptions.*;
import de.aittr.bio_marketplace.repository.AddressRepository;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.AddressService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.mapping.AddressMapper;
import de.aittr.bio_marketplace.service.mapping.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper;

    public AddressServiceImpl(AddressRepository repository, AddressMapper addressMapper) {
        this.repository = repository;
        this.mapper = addressMapper;
    }

    @Override
    public AddressDto save(AddressDto address) {
        try {
            Address entity = mapper.mapDtoToEntity(address);
            entity = repository.save(entity);
            return mapper.mapEntityToDto(entity);
        }  catch (Exception e) {
            throw new AddressValidationException(e);
        }
    }

    @Override
    public List<AddressDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @Override
    public AddressDto getAddressById(Long id) {
        return mapper.mapEntityToDto(getAddressEntityById(id));
    }

    public Address getAddressEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));}

    @Override
    public List<AddressDto> getAllAddressesByUserId(Long userId) {
        try {
        return repository.findAll()
                .stream()
                .filter(address -> address.getUser() != null && address.getUser().getId().equals(userId))
                .map(mapper::mapEntityToDto)
                .toList();
        } catch (AddressByUserNotFoundException e) {
            return List.of();
          }
        }

    @Override
    public List<AddressDto> getAllAddressesBySellerId(Long sellerId) {
        try {
            return repository.findAll()
                    .stream()
                    .filter(address -> address.getSeller() != null && address.getSeller().getId().equals(sellerId))
                    .map(mapper::mapEntityToDto)
                    .toList();
        } catch (AddressBySellerNotFoundException e) {
            return List.of();
        }
    }


    @Override
    public void update(AddressDto address) {
        Long id = address.getId();
        Address findAddress = getAddressEntityById(id);
        findAddress.setCity(address.getCity());
        findAddress.setCountry(address.getCountry());
        findAddress.setStreet(address.getStreet());
        findAddress.setZipCode(address.getZipCode());

    }

    @Override
    public void deleteById(Long id) {
        getAddressEntityById(id);
        repository.deleteById(id);

    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
