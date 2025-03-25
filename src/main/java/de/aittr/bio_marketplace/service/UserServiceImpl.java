package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    //private final UserMappingService mappingService;
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        //this.mappingService = mappingService;
        this.repository = repository;
    }

    @Override
    public void register(User user) {
//        user.setId(null);
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.setStatus(false);
//        user.setRoles(Set.of(roleService.getRoleUser()));
//
        repository.save(user);
//        emailService.sendConfirmationEmail(user);
    }

    @Override
    @Transactional
    public User saveCustomer(User user) {
//        try {
//            User entity = mappingService.mapDtoToEntity(dto);
//
//            Cart cart = new Cart();
//            cart.setCustomer(entity);
//            entity.setCart(cart);
//
//            entity = repository.save(entity);
//           return mappingService.mapEntityToDto(user);
           return repository.save(user);
//        }  catch (Exception e) {
//            throw new CustomerValidationException(e);
//        }
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
