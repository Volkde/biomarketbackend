package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleToStringConverter {
    public List<String> convert(Set<Role> roles) {
        return roles.stream().map(Role::getTitle).collect(Collectors.toList());
    }
}
