package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.User;

public interface EmailService {
    void sendConfirmationEmail(User user, String code);

    void sendPasswordResetEmail(User user, String jwtToken);

}
