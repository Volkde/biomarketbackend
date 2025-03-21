package de.aittr.bio_marketplace.domain.entity;


import java.util.Objects;
import java.util.Set;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private boolean status;
    private String avatar;


//ToDo реализовать в будущем объекты Cart и Address

    // Связь один-к-одному со стороны той таблицы, в которой нет
    // колонки, которая ссылается на другую таблицу
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)

//    private Cart cart;


//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Address address;


    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
    private Set<Role> roles;


    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "seller_id")
//    )
    private Set<Seller> sellers;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String username, String password,
                String phoneNumber, boolean status, String avatar, Set<Role> roles, Set<Seller> sellers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.avatar = avatar;
        this.roles = roles;
        this.sellers = sellers;
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

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isStatus() {
        return status;
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

    public void setStatus(boolean status) {
        this.status = status;
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
        User user = (User) o;
        return status == user.status && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(avatar, user.avatar) && Objects.equals(roles, user.roles) && Objects.equals(sellers, user.sellers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password, phoneNumber, status, avatar, roles, sellers);
    }

    @Override
    public String toString() {
        return String.format(" User: ID - %d, First name - %s, Last name - %s, Email - %s, Username - %s, Password - %s, Phone number  - %s, Status - %s, Avatar - %s, Roles - %s, Sellers - %s.",
                id, firstName, lastName, email, username, password, phoneNumber, status, avatar, roles, sellers);
    }
}
