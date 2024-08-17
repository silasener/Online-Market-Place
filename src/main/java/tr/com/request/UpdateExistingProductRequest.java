package tr.com.obss.jip.finalproject.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.finalproject.enums.Brand;
import tr.com.obss.jip.finalproject.enums.Category;

@Getter
@Setter
public class UpdateExistingProductRequest {
    private Category category;

    private Brand brand;

    private String name;
}
