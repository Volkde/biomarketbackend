package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where upper(u.email) = upper(?1)")
    Optional<User> findUserByEmail(String email);

    @Query("select u from User u where upper(u.email) = upper(?1)")
    Optional<User> findUserByUsername(String email);

    void deleteByUsername(@NotNull(message = "User email cannot be null") @NotBlank(message = "User email cannot be empty") @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "User email should be at least three characters length and start with capital letter"
    ) String username);
}
