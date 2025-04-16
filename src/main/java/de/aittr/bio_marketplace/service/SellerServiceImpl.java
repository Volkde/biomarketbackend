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
import de.aittr.bio_marketplace.service.interfaces.*;
import de.aittr.bio_marketplace.service.mapping.SellerMapper;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository repository;
    private final SellerMapper mapper;
    private final UserLookupService userService;
    private final RoleService roleService;
    private final ReviewService reviewService;
    private final ProductService productService;

    public SellerServiceImpl(SellerRepository repository, SellerMapper sellerMapper, UserLookupService userService, RoleService roleService, ReviewService reviewService,@Lazy ProductService productService) {
        this.repository = repository;
        this.mapper = sellerMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.reviewService = reviewService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public SellerResponse saveSeller(SellerDto seller, Long user_id) {
        try {
            Seller entity = mapper.mapDtoToEntity(seller);
            entity.setId(null);
            entity.setRating(null);
            entity.setActive(true);
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
            return new SellerResponse(mapper.mapEntityToDto(entity),convertUserToDto(user));
        }  catch (Exception e) {
            throw new SellerValidationException(e);
        }
    }

    private UserDto convertUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAvatar(user.getAvatar());
        return dto;
    }


    @Override
    public List<SellerDto> getAllActiveSellers() {
        return repository.findAll()
                .stream()
                .filter(Seller::isActive)
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
        return convertUserToDto(findSeller.getUser());
    }

    @Override
    public Seller getActiveSellersEntityById(Long id) {
        return repository.findById(id)
                .filter(Seller::isActive)
                .orElseThrow(()-> new SellerNotFoundException(id));
    }

    @Override
    @Transactional
    public SellerDto deleteById(Long id) {
         Seller seller = getActiveSellersEntityById(id);
         reviewService.deleteAllReviewsBySellerId(id);
         productService.deleteAllBySellerId(id);
         seller.getUser().getRoles().remove(roleService.getRoleSeller());
         seller.getUser().setSeller(null);
         repository.deleteById(id);
         return mapper.mapEntityToDto(seller);
    }

    @Override
    @Transactional
    public SellerDto deactivateById(Long id) {
        Seller seller = getActiveSellersEntityById(id);
        seller.setActive(false);
        return mapper.mapEntityToDto(seller);
    }

    @Override
    public SellerDto deactivateByStoreName(String storeName) {
        Seller seller = repository.findByStoreName(storeName).orElseThrow(()-> new SellerNotFoundException(storeName));
        seller.setActive(false);
        return mapper.mapEntityToDto(seller);
    }

    @Override
    @Transactional
    public SellerDto deleteByStoreName(String storeName) {
        Seller seller = repository.findByStoreName(storeName).orElseThrow(()-> new SellerNotFoundException(storeName));
        reviewService.deleteAllReviewsBySellerId(seller.getId());
        productService.deleteAllBySellerId(seller.getId());
        seller.getUser().getRoles().remove(roleService.getRoleSeller());
        seller.getUser().setSeller(null);
        repository.deleteByStoreName(storeName);
        return mapper.mapEntityToDto(seller);
    }
}
