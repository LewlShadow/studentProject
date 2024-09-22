package com.tuanda.controller;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @SneakyThrows
    @GetMapping("/get-profile-info")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(String.format("Test %s ", authentication.getName()));
    }

}
