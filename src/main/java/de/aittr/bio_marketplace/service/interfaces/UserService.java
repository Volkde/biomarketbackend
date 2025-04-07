package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    RegisterUserResponseDto registerUser(RegisterUserDto registerUserDto);

    RegisterUserResponseDto loginUser(String email, String password);

    RegisterUserResponseDto getCurrentUser();

    List<UserDto> getAllActiveUsers();

    UserDto getById(Long id);

    UserDto update(UserDto user);

    long getAllActiveUsersCount();

    UserDto activateUser(Long id);

    User getActiveUserEntityById(Long id);

    UserDto deactivateUserById(Long id);

    UserDto deleteById(Long id);

    UserDto deleteByUsername(String username);

    BigDecimal getUsersCartTotalCost(Long userId);

    List<ProductDto> getAllProductsByUserId(Long userId);

    List<SellerDto> getAllSellers(Long userId);

    void addProductToUserCart(Long userId, Long productId);

    void removeProductFromUserCart(Long userId, Long productId);

    void clearUserCart(Long userId);

    BigDecimal getUserProductsAveragePrice(Long userId);

    RegisterUserResponseDto giveAdmin(Long userId);

    RegisterUserResponseDto removeAdmin(Long userId);

    UserDto changePassword(String password, Long userId);
}
