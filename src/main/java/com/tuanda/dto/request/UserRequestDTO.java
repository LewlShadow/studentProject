package com.tuanda.dto.request;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String password;
    private String role;
    private String gender;
    private String userFullName;
}
