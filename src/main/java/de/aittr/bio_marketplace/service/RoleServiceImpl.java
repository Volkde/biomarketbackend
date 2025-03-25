package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Role;
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
                () -> new RuntimeException("ROLE_USER doesn't exist in DB")
        );
    }
}
