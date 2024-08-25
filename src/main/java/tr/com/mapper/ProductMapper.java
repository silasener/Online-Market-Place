package tr.com.mapper;

import org.springframework.stereotype.Component;
import tr.com.dto.ProductDto;
import tr.com.model.Product;
import tr.com.utils.StringUtils;

import java.util.UUID;

@Component
public class ProductMapper {

    public Product toModel(ProductDto dto) {
       Product product = new Product();
        if (StringUtils.isNotBlank(dto.getId())) {
            product.setId(UUID.fromString(dto.getId()));
        }
        product.setName(dto.getName());
        product.setAvailable(Boolean.TRUE);
        product.setBrand(dto.getBrand());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(dto.getCategory());
        product.setCreatedAt(dto.getCreatedAt());
        product.setUpdatedAt(dto.getUpdatedAt());

        return product;
    }

    public ProductDto toDto(Product model) {
        ProductDto dto = new ProductDto();
        dto.setId(model.getId().toString());
        dto.setName(model.getName());
        dto.setBrand(model.getBrand());
        dto.setImageUrl(model.getImageUrl());
        dto.setCategory(model.getCategory());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());

        return dto;
    }


}
