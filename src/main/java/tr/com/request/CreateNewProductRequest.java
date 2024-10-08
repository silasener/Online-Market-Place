package tr.com.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tr.com.enums.Brand;
import tr.com.enums.Category;

@Getter
@Setter
public class CreateNewProductRequest {
    @NotNull
    private Category category;

    @NotNull
    private Brand brand;

    @NotNull
    private String name;

    @NotNull
    private String imageUrl;

    private Boolean available = Boolean.TRUE;
}
