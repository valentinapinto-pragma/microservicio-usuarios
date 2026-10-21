package com.example.users.infrastructure.controller;

import com.example.users.application.dto.request.LoginRequestDto;
import com.example.users.application.dto.response.TokenResponseDto;
import com.example.users.application.handler.AuthHandler;
import com.example.users.domain.exception.InvalidCredentialsException;
import com.example.users.infrastructure.exceptionhandler.ControllerAdvisor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(ControllerAdvisor.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthHandler authHandler;

    @Test
    @DisplayName("Debe responder 200 OK con el token cuando las credenciales son válidas")
    void login_WhenCredentialsAreValid_ShouldReturn200OkWithToken() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto("valentina@correo.com", "PasswordSeguro123");
        TokenResponseDto responseDto = new TokenResponseDto("jwt.token.valido");

        when(authHandler.login(any(LoginRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt.token.valido"));

        verify(authHandler).login(any(LoginRequestDto.class));
    }

    @Test
    @DisplayName("Debe responder 400 Bad Request cuando el formato del email es inválido")
    void login_WhenEmailIsInvalidFormat_ShouldReturn400BadRequest() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto("correo-sin-arroba", "PasswordSeguro123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());

        verify(authHandler, never()).login(any());
    }

    @Test
    @DisplayName("Debe responder 400 Bad Request cuando la contraseña está en blanco")
    void login_WhenPasswordIsBlank_ShouldReturn400BadRequest() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto("valentina@correo.com", "   ");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());

        verify(authHandler, never()).login(any());
    }

    @Test
    @DisplayName("Debe responder 401 Unauthorized cuando las credenciales son incorrectas")
    void login_WhenCredentialsAreInvalid_ShouldReturn401Unauthorized() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto("valentina@correo.com", "ClaveEquivocada");

        when(authHandler.login(any(LoginRequestDto.class)))
                .thenThrow(new InvalidCredentialsException("Invalid email or password"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid email or password"));

        verify(authHandler).login(any(LoginRequestDto.class));
    }
}
