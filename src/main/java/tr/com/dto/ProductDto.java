package tr.com.obss.jip.finalproject.dto;

import lombok.*;
import tr.com.obss.jip.finalproject.enums.Brand;
import tr.com.obss.jip.finalproject.enums.Category;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;

    private String name;

    private Category category;

    private Brand brand;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
