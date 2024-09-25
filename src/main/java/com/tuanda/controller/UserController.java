package com.tuanda.controller;

import com.tuanda.common.EntityResponse;
import com.tuanda.dto.request.UserRequestDTO;
import com.tuanda.service.CommonUserService;
import com.tuanda.service.JwtUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private JwtUserDetailsService userService;

    @SneakyThrows
    @GetMapping("/get-profile-info")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        return EntityResponse.generateSuccessResponse(userService.findByUsername(authentication.getName()));
    }


    @SneakyThrows
    @PutMapping("/update-profile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserRequestDTO dto) {
        return EntityResponse.generateSuccessResponse(userService.updateProfile(dto));
    }

    @SneakyThrows
    @GetMapping("/validate")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> validateToken(Authentication authentication) {
        return EntityResponse.generateSuccessResponse(authentication.getName());
    }
}
