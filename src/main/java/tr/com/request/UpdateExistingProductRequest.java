package tr.com.request;

import lombok.Getter;
import lombok.Setter;
import tr.com.enums.Brand;
import tr.com.enums.Category;

@Getter
@Setter
public class UpdateExistingProductRequest {
    private Category category;

    private Brand brand;

    private String name;

    private String imageUrl;
}
