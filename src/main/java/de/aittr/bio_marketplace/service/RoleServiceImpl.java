package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Role;
import de.aittr.bio_marketplace.exception_handling.exceptions.RoleNotFoundException;
import de.aittr.bio_marketplace.repository.RoleRepository;
import de.aittr.bio_marketplace.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role getRoleUser() {
        return repository.findByTitle("ROLE_USER").orElseThrow(
                () -> new RoleNotFoundException("ROLE_USER")
        );
    }

    @Override
    public Role getRoleAdmin() {
        return repository.findByTitle("ROLE_ADMIN").orElseThrow(
                () -> new RoleNotFoundException("ROLE_ADMIN")
        );
    }

    @Override
    public Role getRoleSeller() {
        return repository.findByTitle("ROLE_SELLER").orElseThrow(
                () -> new RoleNotFoundException("ROLE_SELLER")
        );
    }

}
