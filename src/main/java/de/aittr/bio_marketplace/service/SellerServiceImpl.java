package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.controller.responses.SellerResponse;
import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exception_handling.exceptions.SellerNotFoundException;
import de.aittr.bio_marketplace.exception_handling.exceptions.SellerValidationException;
import de.aittr.bio_marketplace.exception_handling.exceptions.UsernameValidateException;
import de.aittr.bio_marketplace.exception_handling.utils.StringValidator;
import de.aittr.bio_marketplace.repository.SellerRepository;
import de.aittr.bio_marketplace.service.interfaces.RoleService;
import de.aittr.bio_marketplace.service.interfaces.SellerService;
import de.aittr.bio_marketplace.service.interfaces.UserLookupService;
import de.aittr.bio_marketplace.service.mapping.SellerMapper;
import de.aittr.bio_marketplace.service.mapping.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository repository;
    private final SellerMapper mapper;
    private final UserMapper userMapper;
    private final UserLookupService userService;
    private final RoleService roleService;

    public SellerServiceImpl(SellerRepository repository, SellerMapper sellerMapper, UserMapper userMapper, UserLookupService userService, RoleService roleService) {
        this.repository = repository;
        this.mapper = sellerMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public SellerResponse saveSeller(SellerDto seller, Long user_id) {
        try {
            Seller entity = mapper.mapDtoToEntity(seller);
            entity.setId(null);
            entity.setRating(null);
            StringValidator.isValidStoreName(entity.getStoreName());
            if (repository.findByStoreName(entity.getStoreName()).isPresent()) {
                throw new UsernameValidateException("This Store Name already exists");
            }
            User user = userService.getActiveUserEntityById(user_id);
            if(user.getSeller()!= null){
                throw new UsernameValidateException("This user already has a store");
            }
            entity = repository.save(entity);
            user.getRoles().add(roleService.getRoleSeller());
            user.setSeller(entity);
            entity.setUser(user);
            return new SellerResponse(mapper.mapEntityToDto(entity), userMapper.mapEntityToDto(user));
        }  catch (Exception e) {
            throw new SellerValidationException(e);
        }
    }

    @Override
    public List<SellerDto> getAllActiveSellers() {
        return repository.findAll()
                .stream()
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @Override
    public SellerDto getById(Long id) {
        return mapper.mapEntityToDto(getActiveSellersEntityById(id));
    }

    @Override
    @Transactional
    public SellerDto update(SellerDto seller) {
        Long id = seller.getId();
        Seller findSeller = getActiveSellersEntityById(id);

        if (seller.getStoreName() == null){
            seller.setStoreName(findSeller.getStoreName());
        }
        StringValidator.isValidStoreName(seller.getStoreName());
        findSeller.setStoreName(seller.getStoreName());

        if (seller.getStoreDescription() == null){
            seller.setStoreDescription(findSeller.getStoreDescription());
        }
        findSeller.setStoreDescription(seller.getStoreDescription());

        if (seller.getStoreLogo() == null){
            seller.setStoreLogo(findSeller.getStoreLogo());
        }
        findSeller.setStoreLogo(seller.getStoreLogo());
        return mapper.mapEntityToDto(findSeller);
    }

    @Override
    @Transactional
    public SellerDto changeUser(Long user_id, Long seller_id) {
        User user = userService.getActiveUserEntityById(user_id);
        if(user.getSeller()!= null){
            throw new UsernameValidateException("This user already has a store");
        }
        Seller findSeller = getActiveSellersEntityById(seller_id);
        findSeller.getUser().getRoles().remove(roleService.getRoleSeller());
        findSeller.getUser().setSeller(null);
        user.getRoles().add(roleService.getRoleSeller());
        user.setSeller(findSeller);
        findSeller.setUser(user);
        return mapper.mapEntityToDto(findSeller);
    }

    @Override
    @Transactional
    public UserDto getUserBySellerId(Long id) {
        Seller findSeller = getActiveSellersEntityById(id);
        return userMapper.mapEntityToDto(findSeller.getUser());
    }

    @Override
    public Seller getActiveSellersEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new SellerNotFoundException(id));
    }

    @Override
    public SellerDto deleteById(Long id) {
         Seller seller = getActiveSellersEntityById(id);
         seller.getUser().getRoles().remove(roleService.getRoleSeller());
         seller.getUser().setSeller(null);
         repository.deleteById(id);
         return mapper.mapEntityToDto(seller);
    }

    @Override
    @Transactional
    public SellerDto deleteByStoreName(String storeName) {
        Seller seller = repository.findByStoreName(storeName).orElseThrow(()-> new SellerNotFoundException(storeName));
        seller.getUser().getRoles().remove(roleService.getRoleSeller());
        seller.getUser().setSeller(null);
        repository.deleteByStoreName(storeName);
        return mapper.mapEntityToDto(seller);
    }
}
