package com.tuanda.dto.request;

import com.tuanda.common.Constants;
import lombok.Data;

@Data
public class UserRequestDTO {
    private Long id;
    private String email;
    private String password;
    private String userFullName;
    private String username;
    //set default role for user
    private String role = Constants.Role.USER;
}
