package tr.com.obss.jip.finalproject.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.finalproject.enums.Brand;
import tr.com.obss.jip.finalproject.enums.Category;

@Getter
@Setter
public class CreateNewProductRequest {
    @NotNull
    private Category category;

    @NotNull
    private Brand brand;

    @NotNull
    private String name;

    private Boolean available = Boolean.TRUE;
}
