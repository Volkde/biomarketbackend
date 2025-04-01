package de.aittr.bio_marketplace.domain.dto;

import java.util.Objects;

public class AddressDto {

    private Long id;
    private String country;
    private String city;
    private String street;
    private int zipCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto that = (AddressDto) o;
        return zipCode == that.zipCode && Objects.equals(id, that.id) && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, zipCode);
    }

    @Override
    public String toString() {
        return String.format("Address: Id - %d, Country - %s, City - %s, Street - %s, zipCode - %d.", id, country, city, street, zipCode);
    }
}
