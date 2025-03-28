package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exceptiions.AuthenticationException;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.security.service.JwtTokenService;
import de.aittr.bio_marketplace.service.interfaces.RoleService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final String PASSWORD_OR_EMAIL_IS_INCORRECT = "Password or email is incorrect";

    //private final UserMappingService mappingService;
    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public UserServiceImpl(UserRepository repository,
                           RoleService roleService,
                           PasswordEncoder encoder,
                           AuthenticationManager authenticationManager,
                           JwtTokenService jwtTokenService) {
        //this.mappingService = mappingService;
        this.repository = repository;
        this.roleService = roleService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    @Transactional
    public RegisterUserResponseDto registerUser(RegisterUserDto registerDto) {
        User user = registerDto.toUser();
        user.setPassword(encoder.encode(registerDto.password()));
//      ToDo Confirmation Email (false)
        user.setIsActive(true);
        user.setRoles(Set.of(roleService.getRoleUser()));
        Cart cart = new Cart(user);
        user.setCart(cart);
        return RegisterUserResponseDto.fromUser(repository.save(user));
    }


    @Override
    public void loginUser(String email, String password) {
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
        } catch (DisabledException | LockedException | BadCredentialsException ex) {
            throw new AuthenticationException(PASSWORD_OR_EMAIL_IS_INCORRECT);
        }
    }

    @Override
    public List<User> getAllActiveUsers() {
        return repository.findAll()
                .stream()
                .filter(User::isActive)
                //           .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void update(User user) {
        Long id = user.getId();
        User findUser = repository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() -> new RuntimeException("User not found"));
        findUser.setFirstName(user.getFirstName());
        findUser.setLastName(user.getLastName());
        findUser.setUsername(user.getUsername());
        findUser.setPhoneNumber(user.getPhoneNumber());
        findUser.setAvatar(user.getAvatar());
    }


    @Override
    public boolean activateUser(String confirmationCode) {
        return false;
    }

    @Override
    public User getActiveUserEntityById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }
}
