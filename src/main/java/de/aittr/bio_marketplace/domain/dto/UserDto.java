package de.aittr.bio_marketplace.domain.dto;

import java.util.Objects;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private String avatar;
    private Long wishlistId;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public Long getWishlistId() {
        return wishlistId;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(phoneNumber, userDto.phoneNumber) &&
                Objects.equals(avatar, userDto.avatar) &&
                Objects.equals(wishlistId, userDto.wishlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, phoneNumber, avatar, wishlistId);
    }

    @Override
    public String toString() {
        return String.format("User: ID - %d, First name - %s, Last name - %s, Email - %s, Username - %s, Phone number - %s, Avatar - %s, Wishlist ID - %s.",
                id, firstName, lastName, email, username, phoneNumber, avatar, wishlistId);
    }
}

