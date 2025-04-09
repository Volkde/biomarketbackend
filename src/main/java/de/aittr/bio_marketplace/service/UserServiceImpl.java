package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.CartItem;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exceptions.AuthenticationException;
import de.aittr.bio_marketplace.exception_handling.exceptions.UserNotFoundException;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import de.aittr.bio_marketplace.service.interfaces.RoleService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.mapping.ProductMapper;
import de.aittr.bio_marketplace.service.mapping.RegisterUserMapper;
import de.aittr.bio_marketplace.service.mapping.SellerMapper;
import de.aittr.bio_marketplace.service.mapping.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final String PASSWORD_OR_EMAIL_IS_INCORRECT = "Password or email is incorrect";
    private static final String COOKIE_IS_INCORRECT = "Cookie is incorrect";

    private final RegisterUserMapper mappingRegisterService;
    private final UserMapper mappingService;
    private final ProductMapper mappingProductService;
    private final SellerMapper mappingSellerService;
    private final ProductService productService;
    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(RegisterUserMapper registerUserMapper,
                           UserMapper userMapper, ProductMapper productMapper, SellerMapper sellerMapper,
                           ProductService productService,
                           UserRepository repository,
                           RoleService roleService,
                           PasswordEncoder encoder,
                           AuthenticationManager authenticationManager) {
        this.mappingRegisterService = registerUserMapper;
        this.mappingService = userMapper;
        this.mappingProductService = productMapper;
        this.mappingSellerService = sellerMapper;
        this.productService = productService;
        this.repository = repository;
        this.roleService = roleService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public RegisterUserResponseDto registerUser(RegisterUserDto registerDto) {

        if (registerDto.username() == null || registerDto.username().isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        User entity = mappingRegisterService.mapRegisterDtoToEntity(registerDto);

        entity.setId(null);
        entity.setPassword(encoder.encode(registerDto.password()));
        entity.setIsActive(true);
        entity.setRoles(Set.of(roleService.getRoleUser()));
        Cart cart = new Cart(entity);
        entity.setCart(cart);
        entity = repository.save(entity);
        return mappingRegisterService.mapEntityToRegisterResponseDto(entity);
    }

    @Override
    public RegisterUserResponseDto loginUser(String email, String password) {
        User user = repository.findUserByEmail(email)
                .orElseThrow(() -> new AuthenticationException(PASSWORD_OR_EMAIL_IS_INCORRECT));
        boolean isMatch = encoder.matches(password, user.getPassword());

        if (!isMatch) {
            throw new AuthenticationException(PASSWORD_OR_EMAIL_IS_INCORRECT);
        }

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            Authentication authenticated = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            return mappingRegisterService.mapEntityToRegisterResponseDto(user);

        } catch (DisabledException | LockedException | BadCredentialsException ex) {
            throw new AuthenticationException(PASSWORD_OR_EMAIL_IS_INCORRECT);
        }
    }

    @Override
    public RegisterUserResponseDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return mappingRegisterService.mapEntityToRegisterResponseDto(repository.findUserByEmail(email)
                .orElseThrow(() -> new AuthenticationException(COOKIE_IS_INCORRECT)));
    }

    @Override
    public List<UserDto> getAllActiveUsers() {
        return repository.findAll()
                .stream()
                .filter(User::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public UserDto getById(Long id) {
        return mappingService.mapEntityToDto(getActiveUserEntityById(id));
    }

    @Override
    @Transactional
    public UserDto update(UserDto user) {
        Long id = user.getId();
        User findUser = getActiveUserEntityById(id);
        findUser.setFirstName(user.getFirstName());
        findUser.setLastName(user.getLastName());
        findUser.setUsername(user.getUsername());
        findUser.setPhoneNumber(user.getPhoneNumber());
        findUser.setAvatar(user.getAvatar());
        return mappingService.mapEntityToDto(findUser);
    }


    @Override
    public UserDto activateUser(Long id) {
        User findUser = getActiveUserEntityById(id);
        findUser.setIsActive(true);
        return mappingService.mapEntityToDto(findUser);
    }

    @Override
    public User getActiveUserEntityById(Long id) {
        return repository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserDto deactivateUserById(Long id) {
        User findUser = getActiveUserEntityById(id);
        findUser.setIsActive(false);
        return mappingService.mapEntityToDto(findUser);
    }

    @Override
    public UserDto deleteById(Long id) {
        User findUser = getActiveUserEntityById(id);
        repository.deleteById(id);
        return mappingService.mapEntityToDto(findUser);
    }

    @Override
    @Transactional
    public UserDto deleteByUsername(String username) {
        User findUser = repository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        repository.deleteByUsername(username);
        return mappingService.mapEntityToDto(findUser);
    }

    @Override
    public BigDecimal getUsersCartTotalCost(Long userId) {
        User user = getActiveUserEntityById(userId);
        return user.getCart().getActiveProductsTotalCost();
    }

//    @Override
//    public List<CartItem> getAllProductsByUserId(Long userId) {
//        User user = getActiveUserEntityById(userId);
//        return user.getCart().getItems()
//                .stream()
//                .map(mappingProductService::mapEntityToDto)
//                .toList();
//    }
    // TODO: fix it later

    @Override
    public List<SellerDto> getAllSellers(Long userId) {
        User user = getActiveUserEntityById(userId);
        return user.getSellers()
                .stream()
                .map(mappingSellerService :: mapEntityToDto)
                .toList();
    }

    @Override
    @Transactional
    public void addProductToUserCart(Long userId, Long productId, BigDecimal quantity) {
        User user = getActiveUserEntityById(userId);
        Product product = productService.getActiveProductEntityById(productId);
        user.getCart().addProduct(product, quantity);
    }

    @Override
    public void removeProductFromUserCart(Long userId, Long productId) {
        User user = getActiveUserEntityById(userId);
        user.getCart().removeProductById(productId);
    }

    @Override
    public void clearUserCart(Long userId) {
        User user = getActiveUserEntityById(userId);
        user.getCart().clear();
    }

    @Override
    public long getAllActiveUsersCount() {
        return repository.findAll()
                .stream()
                .filter(User::isActive)
                .count();
    }

    @Override
    public BigDecimal getUserProductsAveragePrice(Long userId) {
        User user = getActiveUserEntityById(userId);
        return user.getCart().getActiveProductsAveragePrice();
    }

    @Override
    @Transactional
    public RegisterUserResponseDto giveAdmin(Long userId) {
        User user = getActiveUserEntityById(userId);
        user.getRoles().add(roleService.getRoleAdmin());
        return mappingRegisterService.mapEntityToRegisterResponseDto(user);
    }

    @Override
    @Transactional
    public RegisterUserResponseDto removeAdmin(Long userId) {
        User user = getActiveUserEntityById(userId);
        user.getRoles().remove(roleService.getRoleAdmin());
        return mappingRegisterService.mapEntityToRegisterResponseDto(user);
    }

    @Override
    @Transactional
    public UserDto changePassword(String password, Long userId) {
        if (password.length() < 6){
            //todo password check
            return null;
        }
        User user = getActiveUserEntityById(userId);
        user.setPassword(encoder.encode(password));
        return mappingService.mapEntityToDto(user);
    }

}