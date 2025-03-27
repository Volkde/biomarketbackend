package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    void loginUser(String email, String password);

    List<User> getAllActiveUsers();

    User getById(Long id);

    void update(User user);

    boolean activateUser(String confirmationCode);

    User getActiveUserEntityById(Long id);

    void deleteById(Long id);

    void deleteByUsername(String username);
}
