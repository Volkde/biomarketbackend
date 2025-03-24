package de.aittr.bio_marketplace.domain.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "storeName", nullable = false)
    @NotNull(message = "Seller storeName cannot be null")
    @NotBlank(message = "Seller storeName cannot be empty")
    @Pattern(
            regexp = "[A-Z][a-z ]{2,}",
            message = "Seller storeName should be at least three characters length and start with capital letter"
    )
    private String storeName;

    @Column(name = "storeDescription")
    private String storeDescription;

    @Column(name = "storeLogo")
    private String storeLogo;

    @Column(name = "rating")
    private BigDecimal rating;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    private User user;

        @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "seller_role",
            joinColumns = @JoinColumn(name = "seller_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public Seller() {
    }

    public Seller(Long id, String storeName, String storeDescription, String storeLogo, BigDecimal rating, Set<Role> roles) {
        this.id = id;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.storeLogo = storeLogo;
        this.rating = rating;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id) && Objects.equals(storeName, seller.storeName)
                && Objects.equals(storeDescription, seller.storeDescription)
                && Objects.equals(storeLogo, seller.storeLogo) && Objects.equals(rating, seller.rating)
                && Objects.equals(roles, seller.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeName, storeDescription, storeLogo, rating, roles);
    }

    @Override
    public String toString() {
        return String.format(" Seller: ID - %d, Store name - %s, Description - %s, Logo - %s, Rating - %.2f, Roles - %s.",
                id, storeName, storeDescription, storeLogo, rating, roles);
    }
}
