package com.tuanda.controller;

import com.tuanda.config.JWTTokenUtil;
import com.tuanda.dto.request.LoginRequestDTO;
import com.tuanda.dto.request.UserRequestDTO;
import com.tuanda.dto.response.AuthenticationResponseDTO;
import com.tuanda.service.JwtUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.tuanda.common.EntityResponse;


@RestController
@CrossOrigin
public class JwtAuthenticationController extends BaseController{

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @SneakyThrows
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDTO loginRequestDTO){

        try {
            authenticate(loginRequestDTO);
        } catch (Exception e) {
            return EntityResponse.generateResponse("Authentication Failed", HttpStatus.OK,
                    "Invalid credentials, please check details and try again.");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequestDTO.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseDTO(token, refreshToken));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserRequestDTO requestDTO) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(requestDTO));
    }

}
