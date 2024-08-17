package tr.com.obss.jip.finalproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import tr.com.obss.jip.finalproject.model.Product;
import tr.com.obss.jip.finalproject.model.User;

import java.time.LocalDateTime;
import java.util.List;

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

