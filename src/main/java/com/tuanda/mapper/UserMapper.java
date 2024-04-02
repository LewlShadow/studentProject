package com.tuanda.mapper;

import com.tuanda.dto.request.UserRequestDTO;
import com.tuanda.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", source = "password")
    User mapToUser(UserRequestDTO userRequestDTO, String password);
}