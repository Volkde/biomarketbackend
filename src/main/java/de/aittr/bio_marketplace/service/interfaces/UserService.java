package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    RegisterUserResponseDto registerUser(RegisterUserDto registerUserDto);

    void loginUser(String email, String password);

    RegisterUserResponseDto getCurrentUser();

    List<UserDto> getAllActiveUsers();

    UserDto getById(Long id);

    void update(UserDto user);

    long getAllActiveUsersCount();

    boolean activateUser(String confirmationCode);

    User getActiveUserEntityById(Long id);

    void deleteById(Long id);

    void deleteByUsername(String username);

    BigDecimal getUsersCartTotalCost(Long userId);

    List<ProductDto> getAllProductsByUserId(Long userId);

    void addProductToUserCart(Long userId, Long productId);

    void removeProductFromUserCart(Long userId, Long productId);

    void clearUserCart(Long userId);

    BigDecimal getUserProductsAveragePrice(Long userId);
}
