package tr.com.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import tr.com.dto.UserDto;
import tr.com.model.User;

@Component
@Mapper(componentModel = "spring") //mapStruct dependency
public interface UserMapper {

    User toModel(UserDto dto);

    UserDto toDto(User model);
}

