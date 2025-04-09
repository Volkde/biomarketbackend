package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.ConfirmationCode;
import de.aittr.bio_marketplace.domain.entity.User;

import java.util.Optional;

public interface ConfirmationService {

    String generateConfirmationCode(User user);

    boolean isCodeExpired(String code);

    Optional<ConfirmationCode> findByCode(String code);

    void confirmRegistration(String code);
}
