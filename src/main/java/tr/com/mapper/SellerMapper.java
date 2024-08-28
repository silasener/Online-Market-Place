package tr.com.mapper;

import org.mapstruct.Mapper;
import tr.com.dto.SellerDto;
import tr.com.model.Seller;

@Mapper(componentModel = "spring")
public interface SellerMapper { //mapStruct dependency

    Seller toModel(SellerDto dto);

    SellerDto toDto(Seller model);
}
