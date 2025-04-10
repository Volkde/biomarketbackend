package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.AddressDto;
import de.aittr.bio_marketplace.domain.entity.Address;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exception_handling.exceptions.*;
import de.aittr.bio_marketplace.exception_handling.utils.StringValidator;
import de.aittr.bio_marketplace.repository.AddressRepository;
import de.aittr.bio_marketplace.service.interfaces.AddressService;
import de.aittr.bio_marketplace.service.interfaces.SellerService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.mapping.AddressMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AddressServiceImpl implements AddressService {

    private final SellerService sellerService;
    private final UserService userService;
    private final AddressRepository repository;
    private final AddressMapper mapper;

    public AddressServiceImpl(SellerService sellerService, UserService userService, AddressRepository repository, AddressMapper addressMapper) {
        this.sellerService = sellerService;
        this.userService = userService;
        this.repository = repository;
        this.mapper = addressMapper;
    }

    @Override
    public AddressDto saveUserAddress(AddressDto address, Long user_id) {
        Address entity = mapper.mapDtoToEntity(address);
        entity.setId(null);
        StringValidator.isValidCountry(entity.getCountry());
        StringValidator.isValidZipCode(entity.getZipCode());
        User user = userService.getActiveUserEntityById(user_id);
        entity.setUser(user);
        entity = repository.save(entity);
        return mapper.mapEntityToDto(entity);
    }

    @Override
    public AddressDto saveSellerAddress(AddressDto address, Long seller_id) {
        Address entity = mapper.mapDtoToEntity(address);
        entity.setId(null);
        StringValidator.isValidCountry(entity.getCountry());
        StringValidator.isValidZipCode(entity.getZipCode());
        Seller seller = sellerService.getActiveSellersEntityById(seller_id);
        entity.setSeller(seller);
        entity = repository.save(entity);
        return mapper.mapEntityToDto(entity);
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
    @Transactional
    public AddressDto update(AddressDto address) {
        Long id = address.getId();
        Address findAddress = getAddressEntityById(id);
        findAddress.setCity(address.getCity());
        findAddress.setCountry(address.getCountry());
        findAddress.setStreet(address.getStreet());
        findAddress.setZipCode(address.getZipCode());
        return mapper.mapEntityToDto(findAddress);
    }

    @Override
    public AddressDto deleteById(Long id) {
        Address findAddress = getAddressEntityById(id);
        repository.deleteById(id);
        return mapper.mapEntityToDto(findAddress);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
