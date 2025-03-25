package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByTitle(String title);
}
