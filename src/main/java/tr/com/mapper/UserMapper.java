package tr.com.obss.jip.finalproject.mapper;

import org.springframework.stereotype.Component;
import tr.com.obss.jip.finalproject.dto.UserDto;
import tr.com.obss.jip.finalproject.model.User;
import tr.com.obss.jip.finalproject.utils.StringUtils;

import java.util.UUID;

@Component
public class UserMapper {

    public User toModel(UserDto dto) {
        User user = new User();
        if (StringUtils.isNotBlank(dto.getId())) {
            user.setId(UUID.fromString(dto.getId()));
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setSurname(dto.getSurname());
        user.setEnabled(Boolean.TRUE);
        user.setUsername(dto.getUsername());
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        return user;
    }


    public UserDto toDto(User model) {
        UserDto dto = new UserDto();
        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setSurname(model.getSurname());
        dto.setUsername(model.getUsername());
        dto.setId(model.getId().toString());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }
}

