package tr.com.obss.jip.finalproject.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.finalproject.enums.Brand;
import tr.com.obss.jip.finalproject.enums.Category;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Brand brand;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Seller> sellers = new ArrayList<>();

    @Column(nullable = false)
    private Boolean available = Boolean.TRUE;

    @ManyToMany(mappedBy = "favoriteProducts", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<User> favoritedByUsers = new ArrayList<>();
}
