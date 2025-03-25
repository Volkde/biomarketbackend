package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.Seller;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository  extends JpaRepository<Seller, Long> {
    Optional<Seller> findByStoreName(String storeName);

    void deleteByStoreName(@NotNull(message = "Seller name cannot be null") @NotBlank(message = "Seller name cannot be empty") @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "Seller name should be at least three characters length and start with capital letter"
    ) String storeName);
}
