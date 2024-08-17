package tr.com.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tr.com.enums.Brand;
import tr.com.enums.Category;

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

