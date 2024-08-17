package tr.com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 100)
    private String surname;

    @NotBlank
    @Email
    private String email;

    private String password;

    private Boolean enabled;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Product> favoriteProducts = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Seller> blackListedSellers = new ArrayList<>();


    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }


    public void addFavoriteProduct(Product product) {
        this.favoriteProducts.add(product);
        product.getFavoritedByUsers().add(this);
    }

    public void removeFavoriteProduct(Product product) {
        this.favoriteProducts.remove(product);
        product.getFavoritedByUsers().remove(this);
    }

    public void addBlackListedSeller(Seller seller) {
        this.blackListedSellers.add(seller);
        seller.getBlackListedByUsers().add(this);
    }

    public void removeBlackListedSeller(Seller seller) {
        this.blackListedSellers.remove(seller);
        seller.getBlackListedByUsers().remove(this);
    }
}
