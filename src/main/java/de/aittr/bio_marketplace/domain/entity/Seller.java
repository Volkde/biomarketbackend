package de.aittr.bio_marketplace.domain.entity;



import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class Seller {
    private Long id;
    private String storeName;
    private String storeDescription;
    private String storeLogo;
    private BigDecimal rating;
    //ToDo как лучше всего реализовать продавца и ферму, через дополнительный класс фермы
    // с дублированием данных в юзере и селлере, или так как сейчас (вроде и так ок)
    private User user;

    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "seller_role",
//            joinColumns = @JoinColumn(name = "seller_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
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
