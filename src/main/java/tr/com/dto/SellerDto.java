package tr.com.dto;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {
    private String id;

    private String name;

    private String surname;

    private String email;

    private String vendorCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

