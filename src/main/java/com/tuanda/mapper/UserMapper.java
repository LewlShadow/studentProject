package com.tuanda.mapper;

import com.tuanda.dto.request.UserRequestDTO;
import com.tuanda.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @prOjEct studentProject-main
 * @DAtE 4/2/2024
 * @tImE 10:16 AM
 * @AUthOr tuanda52
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", source = "password")
    User mapToUser(UserRequestDTO userRequestDTO, String password);
}
