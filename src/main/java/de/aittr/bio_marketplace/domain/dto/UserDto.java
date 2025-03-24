package de.aittr.bio_marketplace.domain.dto;

import de.aittr.bio_marketplace.domain.entity.Role;
import de.aittr.bio_marketplace.domain.entity.Seller;

import java.util.Objects;
import java.util.Set;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String avatar;
    private Set<Role> roles;
    private Set<Seller> sellers;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<Seller> getSellers() {
        return sellers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setSellers(Set<Seller> sellers) {
        this.sellers = sellers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(email, userDto.email) && Objects.equals(username, userDto.username) && Objects.equals(password, userDto.password) && Objects.equals(phoneNumber, userDto.phoneNumber) && Objects.equals(avatar, userDto.avatar) && Objects.equals(roles, userDto.roles) && Objects.equals(sellers, userDto.sellers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password, phoneNumber, avatar, roles, sellers);
    }

    @Override
    public String toString() {
        return String.format(" User: ID - %d, First name - %s, Last name - %s, Email - %s, Username - %s, Password - %s, Phone number  - %s, Avatar - %s, Roles - %s, Sellers - %s.",
                id, firstName, lastName, email, username, password, phoneNumber, avatar, roles, sellers);
    }
}

