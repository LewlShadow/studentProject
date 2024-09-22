package com.tuanda.controller;

import com.tuanda.common.Constants;
import com.tuanda.config.JWTTokenUtil;
import com.tuanda.custome_exception.InvalidFormatException;
import com.tuanda.dto.request.LoginRequestDTO;
import com.tuanda.dto.request.UserRequestDTO;
import com.tuanda.dto.response.AuthenticationResponseDTO;
import com.tuanda.service.JwtUserDetailsService;
import com.tuanda.utils.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.tuanda.common.EntityResponse;


@RestController
@CrossOrigin
public class JwtAuthenticationController extends BaseController {

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    @SneakyThrows
    @RequestMapping(value = "auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDTO loginRequestDTO) {
        // If user using email convert it to username
        boolean is_valid;
        if (loginRequestDTO.getUsername().contains("@")) {
            is_valid = StringUtils.isValidEmail(loginRequestDTO.getUsername());
            if (is_valid) {
                String username = this.userDetailsService.findUsernameByEmail(loginRequestDTO.getUsername());
                loginRequestDTO.setUsername(username);
            } else return EntityResponse.generateResponse(Constants.Message.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        } else {
            is_valid = StringUtils.isValidUsername(loginRequestDTO.getUsername());
            if (!is_valid)
                return EntityResponse.generateResponse(Constants.Message.INVALID_USERNAME, HttpStatus.BAD_REQUEST);
        }


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

    @RequestMapping(value = "auth/register", method = RequestMethod.POST)
    @SneakyThrows
    public ResponseEntity<?> saveUser(@RequestBody UserRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(userDetailsService.save(requestDTO));
        } catch (InvalidFormatException e) {
            return EntityResponse.generateErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
