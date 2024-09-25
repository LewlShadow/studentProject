package com.tuanda;

import com.tuanda.common.Constants;
import com.tuanda.config.JWTTokenUtil;
import com.tuanda.controller.JwtAuthenticationController;
import com.tuanda.dto.request.LoginRequestDTO;
import com.tuanda.dto.response.AuthenticationResponseDTO;
import com.tuanda.service.JwtUserDetailsService;
import com.tuanda.utils.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    @Mock
    private JwtUserDetailsService userDetailsService;

    @Mock
    private JWTTokenUtil jwtTokenUtil;

    @InjectMocks
    private JwtAuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAuthenticationTokenWithValidEmail() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("test@example.com");
        loginRequestDTO.setPassword("password");

        when(userDetailsService.findUsernameByEmail("test@example.com")).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(mock(UserDetails.class));
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
        when(jwtTokenUtil.generateRefreshToken(any(UserDetails.class))).thenReturn("refreshToken");

        ResponseEntity<?> response = authenticationController.createAuthenticationToken(loginRequestDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testCreateAuthenticationTokenWithInvalidEmail() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("invalid-email");
        loginRequestDTO.setPassword("password");

        ResponseEntity<?> response = authenticationController.createAuthenticationToken(loginRequestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateAuthenticationTokenWithInvalidUsername() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("invalidUsername");
        loginRequestDTO.setPassword("password");


        ResponseEntity<?> response = authenticationController.createAuthenticationToken(loginRequestDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testCreateAuthenticationTokenWithAuthenticationFailure() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("testUser");
        loginRequestDTO.setPassword("wrongPassword");

        JwtAuthenticationController controller = Mockito.mock(JwtAuthenticationController.class);
        doThrow(new RuntimeException("Authentication Failed")).when(controller).authenticate(loginRequestDTO);

        ResponseEntity<?> response = controller.createAuthenticationToken(loginRequestDTO);

        assertNull(response);
    }
}

