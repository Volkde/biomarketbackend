package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Cart;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.RoleService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    //private final UserMappingService mappingService;
    private final UserRepository repository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, RoleService roleService, BCryptPasswordEncoder encoder) {
        //this.mappingService = mappingService;
        this.repository = repository;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public User registerUser(User user) {

        user.setId(null);
        //user.setPassword(encoder.encode(user.getPassword()));
//      ToDo Confirmation Email (false)
        user.setStatus(true);
        user.setRoles(Set.of(roleService.getRoleUser()));
//        try {
//            User entity = mappingService.mapDtoToEntity(dto);
            Cart cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
//            entity = repository.save(entity);
//            return mappingService.mapEntityToDto(user);
//            emailService.sendConfirmationEmail(user);
       return repository.save(user);
//        }  catch (Exception e) {
//            throw new CustomerValidationException(e);
//        }
    }

    @Override
    public void loginUser(String email, String password) {
        User user = repository.findUserByEmail(email)
                .filter(User::isStatus)
                .orElseThrow(() -> new RuntimeException("User not found"));
       encoder.matches(password, user.getPassword());
    }

    @Override
    public List<User> getAllActiveUsers() {
        return repository.findAll()
                .stream()
                .filter(User::isStatus)
     //           .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .filter(User::isStatus)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void update(User user) {
        Long id = user.getId();
        User findUser = repository.findById(id)
                .filter(User::isStatus)
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
