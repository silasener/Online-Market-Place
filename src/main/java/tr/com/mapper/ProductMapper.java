package tr.com.obss.jip.finalproject.mapper;

import org.springframework.stereotype.Component;
import tr.com.obss.jip.finalproject.dto.ProductDto;
import tr.com.obss.jip.finalproject.dto.UserDto;
import tr.com.obss.jip.finalproject.model.Product;
import tr.com.obss.jip.finalproject.model.User;
import tr.com.obss.jip.finalproject.utils.StringUtils;

import java.util.ArrayList;
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
        dto.setCategory(model.getCategory());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());

        return dto;
    }


}
