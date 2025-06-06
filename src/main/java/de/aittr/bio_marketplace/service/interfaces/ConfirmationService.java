package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.User;

public interface ConfirmationService {

    String generateConfirmationCode(User user);

    void confirmRegistration(String code);
}
