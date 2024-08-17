package tr.com.obss.jip.finalproject.mapper;

import org.springframework.stereotype.Component;
import tr.com.obss.jip.finalproject.dto.SellerDto;
import tr.com.obss.jip.finalproject.model.Seller;
import tr.com.obss.jip.finalproject.utils.StringUtils;

import java.util.UUID;


@Component
public class SellerMapper {

    public Seller toModel(SellerDto dto) {
       Seller seller = new Seller();
        if (StringUtils.isNotBlank(dto.getId())) {
            seller.setId(UUID.fromString(dto.getId()));
        }
        seller.setName(dto.getName());
        seller.setVenderCode(dto.getVenderCode());
        seller.setSurname(dto.getSurname());
        seller.setEmail(dto.getEmail());
        seller.setCreatedAt(dto.getCreatedAt());
        seller.setUpdatedAt(dto.getUpdatedAt());
        return seller;
    }


    public SellerDto toDto(Seller model) {
        SellerDto dto = new SellerDto();
        dto.setId(model.getId().toString());
        dto.setVenderCode(model.getVenderCode());
        dto.setName(model.getName());
        dto.setSurname(model.getSurname());
        dto.setEmail(model.getEmail());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }

}
