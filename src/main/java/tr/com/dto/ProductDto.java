package tr.com.dto;

import lombok.*;
import tr.com.enums.Brand;
import tr.com.enums.Category;

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
