package tr.com.dto;

import jakarta.validation.constraints.NotNull;
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

    private String venderCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

