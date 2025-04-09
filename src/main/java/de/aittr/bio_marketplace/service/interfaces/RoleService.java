package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.Role;

public interface RoleService {

    Role getRoleUser();
    Role getRoleAdmin();
    Role getRoleSeller();

    Role getRoleGuest();
}
