package tr.com.obss.jip.finalproject.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.finalproject.enums.Brand;
import tr.com.obss.jip.finalproject.enums.Category;

import java.util.List;

@Getter
@Setter
public class ProductFilterRequest {
    @NotBlank
    private String userId;

    private List<String> productNames;
    private List<Category> categories;
    private List<Brand> brands;
}

