package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    void deleteByName(@NotNull(message = "User name cannot be null") @NotBlank(message = "User name cannot be empty") @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "User name should be at least three characters length and start with capital letter"
    ) String name);
}
