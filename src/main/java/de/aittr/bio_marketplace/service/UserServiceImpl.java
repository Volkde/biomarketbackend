package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.mapping.UserMappingService;

import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {

    private final UserMappingService mappingService;
    private final UserRepository repository;

    public UserServiceImpl(UserMappingService mappingService, UserRepository repository) {
        this.mappingService = mappingService;
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
    public void update(User user) {

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
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }
}
