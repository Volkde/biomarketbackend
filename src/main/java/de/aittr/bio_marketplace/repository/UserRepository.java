package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findUserByEmail(String email);

    void deleteByUsername(@NotNull(message = "User username cannot be null") @NotBlank(message = "User username cannot be empty") @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "User username should be at least three characters length and start with capital letter"
    ) String username);

}
