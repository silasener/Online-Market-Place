package tr.com.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tr.com.enums.Brand;
import tr.com.enums.Category;

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

    private String imageUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Seller> sellers = new ArrayList<>();

    @Column(nullable = false)
    private Boolean available = Boolean.TRUE;

    @ManyToMany(mappedBy = "favoriteProducts", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<User> favoriteByUsers = new ArrayList<>();
}
