package com.example.users.infrastructure.exceptionhandler;

import com.example.users.domain.exception.InvalidCredentialsException;
import com.example.users.domain.exception.UserNotFoundException;
import com.example.users.domain.exception.UserUnderAgeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ControllerAdvisorTest {

    private ControllerAdvisor controllerAdvisor;

    @BeforeEach
    void setUp() {
        controllerAdvisor = new ControllerAdvisor();
    }

    @Test
    @DisplayName("Debe responder 404 NOT_FOUND cuando ocurre UserNotFoundException")
    void handleUserNotFoundException_ShouldReturn404() {
        UserNotFoundException ex = new UserNotFoundException("Usuario no encontrado");
        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleUserNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Debe responder 401 UNAUTHORIZED cuando ocurre InvalidCredentialsException")
    void handleInvalidCredentialsException_ShouldReturn401() {
        InvalidCredentialsException ex = new InvalidCredentialsException("Credenciales invalidas");
        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleInvalidCredentialsException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales invalidas", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Debe responder 400 BAD_REQUEST cuando ocurre UserUnderAgeException")
    void handleUserUnderAgeException_ShouldReturn400() {
        UserUnderAgeException ex = new UserUnderAgeException();
        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleUserUnderAgeException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El usuario debe ser mayor de edad.", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Debe responder 409 CONFLICT cuando ocurre DataIntegrityViolationException (Duplicado)")
    void handleDataIntegrityViolationException_ShouldReturn409() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Duplicate entry");
        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody().get("message"));
    }

    @Test
    @DisplayName("Debe responder 500 INTERNAL_SERVER_ERROR controlado cuando ocurre una excepción imprevista")
    void handleGlobalException_ShouldReturn500() {
        Exception ex = new RuntimeException("Error inesperado");
        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleGlobalException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ha ocurrido un error inesperado en el servidor.", response.getBody().get("message"));
    }
}
