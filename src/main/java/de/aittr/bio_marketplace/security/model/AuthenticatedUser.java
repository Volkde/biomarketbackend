package de.aittr.bio_marketplace.security.model;

import de.aittr.bio_marketplace.domain.entity.Role;
import de.aittr.bio_marketplace.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public record AuthenticatedUser(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user == null) {
            return Collections.emptySet();
        }
        return user.getRoles()
                .stream()
                .map(Role::getTitle)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user != null && user.isStatus();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user != null && user.isStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user != null && user.isStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user != null && user.isStatus();
    }
}
