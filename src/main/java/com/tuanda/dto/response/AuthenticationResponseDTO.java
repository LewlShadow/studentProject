package com.tuanda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @prOjEct studentProject-main
 * @DAtE 4/2/2024
 * @tImE 2:06 PM
 * @AUthOr tuanda52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    private String token;
    private String refresh;
}
