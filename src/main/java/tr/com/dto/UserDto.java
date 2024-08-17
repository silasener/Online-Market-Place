package tr.com.obss.jip.finalproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;

    private String name;

    private String surname;

    private String email;

    private String username;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

