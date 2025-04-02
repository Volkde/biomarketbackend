package de.aittr.bio_marketplace.repository;

import de.aittr.bio_marketplace.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
