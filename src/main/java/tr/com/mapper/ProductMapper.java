package tr.com.mapper;

import org.mapstruct.Mapper;
import tr.com.dto.ProductDto;
import tr.com.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper { //mapStruct dependency

    Product toModel(ProductDto dto);

    ProductDto toDto(Product model);
}
