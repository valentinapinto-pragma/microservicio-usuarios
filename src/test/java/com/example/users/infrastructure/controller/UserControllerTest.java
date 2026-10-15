package com.example.users.infrastructure.controller;

import com.example.users.application.dto.request.UserRequestDto;
import com.example.users.application.handler.UserHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserHandler userHandler;

    private UserRequestDto validDto;

    @BeforeEach
    void setUp() {
        validDto = new UserRequestDto();
        validDto.setName("Valentina");
        validDto.setLastName("Pinto");
        validDto.setIdentityDocument("1002345678");
        validDto.setPhoneNumber("+573001234567");
        validDto.setBirthDate(LocalDate.of(1998, 5, 15));
        validDto.setEmail("valentina@correo.com");
        validDto.setPassword("PasswordSeguro123");
    }

    @Test
    @DisplayName("Debe responder 201 Created cuando los datos de entrada son válidos")
    void createOwner_WhenValidData_ShouldReturn201Created() throws Exception {
        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        verify(userHandler).createOwner(any(UserRequestDto.class));
    }

    @Test
    @DisplayName("Debe responder 400 Bad Request cuando el correo no tiene formato válido")
    void createOwner_WhenEmailIsInvalid_ShouldReturn400BadRequest() throws Exception {
        validDto.setEmail("correo-sin-arroba");

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());

        verify(userHandler, never()).createOwner(any());
    }

    @Test
    @DisplayName("Debe responder 400 Bad Request cuando el documento de identidad no es numérico")
    void createOwner_WhenIdentityDocumentIsNotNumeric_ShouldReturn400BadRequest() throws Exception {
        validDto.setIdentityDocument("100234ABC"); // Contiene letras

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());

        verify(userHandler, never()).createOwner(any());
    }

    @Test
    @DisplayName("Debe responder 400 Bad Request cuando el teléfono supera 13 caracteres")
    void createOwner_WhenPhoneIsTooLong_ShouldReturn400BadRequest() throws Exception {
        validDto.setPhoneNumber("+5730012345678999"); // Más de 13 caracteres

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());

        verify(userHandler, never()).createOwner(any());
    }
}
