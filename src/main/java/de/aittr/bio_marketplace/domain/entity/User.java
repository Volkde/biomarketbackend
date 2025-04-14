package de.aittr.bio_marketplace.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "User firstName should be at least three characters length and start with capital letter"
    )
    private String firstName;

    @Column(name = "last_name")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "User lastName should be at least three characters length and start with capital letter"
    )
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    @NotNull(message = "User username cannot be null")
    @NotBlank(message = "User username cannot be empty")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "User email should be at least three characters length and start with capital letter"
    )
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "wishlist_id")
    private Long wishlistId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "seller_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "seller_id")
    )
    private Set<Seller> sellers = new HashSet<>();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;



    public User() {
    }

    public User(String firstName, String lastName, String email, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }

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

    public Cart getCart() {
        return cart;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getAvatar() {
        return avatar;
    }

    public Long getWishlistId() {
        return wishlistId;
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

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIsActive(boolean status) {
        this.isActive = status;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
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
        User user = (User) o;
        return isActive == user.isActive &&
                Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(avatar, user.avatar) &&
                Objects.equals(wishlistId, user.wishlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password, phoneNumber, isActive, avatar, wishlistId);
    }

    @Override
    public String toString() {
        return String.format("User: ID - %d, First name - %s, Last name - %s, Email - %s, Username - %s, Password - %s, Phone number - %s, Status - %s, Avatar - %s, Wishlist ID - %s.",
                id, firstName, lastName, email, username, password, phoneNumber, isActive, avatar, wishlistId);
    }
}

