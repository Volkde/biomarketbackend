package de.aittr.bio_marketplace.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;


@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", nullable = false)
    @NotNull(message = "Address country cannot be null")
    @NotBlank(message = "Address country cannot be empty")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "Address country should be at least three characters length and start with capital letter"
    )
    private String country;

    @Column(name = "city", nullable = false)
    @NotNull(message = "Address city cannot be null")
    @NotBlank(message = "Address city cannot be empty")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "Address city should be at least three characters length and start with capital letter"
    )
    private String city;

    @Column(name = "street", nullable = false)
    @NotNull(message = "Address street cannot be null")
    @NotBlank(message = "Address street cannot be empty")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "Address street should be at least three characters length and start with capital letter"
    )
    private String street;

    @Column(name = "zip_code", nullable = false)
    @NotNull(message = "Address zipCode cannot be null")
    @NotBlank(message = "Address zipCode cannot be empty")

    private int zipCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address() {
    }

    public Address(Long id, String country, String city, String street, int zipCode, User user) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return zipCode == address.zipCode && Objects.equals(id, address.id) && Objects.equals(country, address.country) && Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(user, address.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, zipCode, user);
    }

    @Override
    public String toString() {
        return String.format("Address: Id - %d, Country - %s, City - %s, Street - %s, zipCode - %d.", id, country, city, street, zipCode);
    }
}
