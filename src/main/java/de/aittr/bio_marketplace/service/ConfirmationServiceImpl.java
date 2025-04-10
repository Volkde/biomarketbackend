package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.ConfirmationCode;
import de.aittr.bio_marketplace.domain.entity.Role;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exception_handling.exceptions.UserNotFoundException;
import de.aittr.bio_marketplace.repository.ConfirmationCodeRepository;
import de.aittr.bio_marketplace.service.interfaces.ConfirmationService;
import de.aittr.bio_marketplace.service.interfaces.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationCodeRepository repository;
    private final RoleService roleService;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository,
                                   RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
    }

    @Override
    public String generateConfirmationCode(User user) {
        String code = UUID.randomUUID().toString();
        LocalDateTime expired = LocalDateTime.now().plusMinutes(10);

        ConfirmationCode entity = new ConfirmationCode(code, expired, user);
        repository.save(entity);
        return code;
    }

    @Override
    @Transactional
    public void confirmRegistration(String code) {
        ConfirmationCode found = repository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Confirmation code not found"));

        if (found.getExpired().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Confirmation code is expired");
        }

        User user = found.getUser();
        if (user == null) {
            throw new UserNotFoundException("No user in code");
        }

        user.setIsActive(true);

        Set<Role> roles = user.getRoles();
        roles.removeIf(r -> r.getTitle().equals("ROLE_GUEST"));
        roles.add(roleService.getRoleUser());
        user.setRoles(roles);

        repository.delete(found);
    }
}
