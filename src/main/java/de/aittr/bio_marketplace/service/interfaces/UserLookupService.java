package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.User;

public interface UserLookupService {
    User getActiveUserEntityById(Long id);
}