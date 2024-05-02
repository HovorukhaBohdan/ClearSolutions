package org.example.clearsolutions.mapper;

import org.example.clearsolutions.config.MapperConfig;
import org.example.clearsolutions.dto.UserRequestDto;
import org.example.clearsolutions.dto.UserResponseDto;
import org.example.clearsolutions.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRequestDto userRequestDto);
}
