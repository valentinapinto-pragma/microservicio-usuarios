package com.example.users.infrastructure.controller;

import com.example.users.application.dto.request.UserRequestDto;
import com.example.users.application.dto.response.UserResponseDto;
import com.example.users.application.handler.UserHandler;
import com.example.users.domain.exception.UserNotFoundException;
import com.example.users.infrastructure.exceptionhandler.ControllerAdvisor;
import com.example.users.infrastructure.security.JwtAdapter;
import com.example.users.infrastructure.security.SecurityConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfiguration.class, ControllerAdvisor.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserHandler userHandler;

    @MockBean
    private JwtAdapter jwtAdapter;

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
    @DisplayName("Debe responder 201 Created cuando los datos de entrada para Owner son válidos")
    void createOwner_WhenValidData_ShouldReturn201Created() throws Exception {
        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        verify(userHandler).createOwner(any(UserRequestDto.class));
    }

    @Test
    @WithMockUser(roles = "OWNER")
    @DisplayName("Debe responder 201 Created cuando el Propietario crea un Empleado")
    void createEmployee_WhenAuthorizedOwner_ShouldReturn201Created() throws Exception {
        mockMvc.perform(post("/users/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        verify(userHandler).createEmployee(any(UserRequestDto.class));
    }

    @Test
    @DisplayName("Debe responder 403 Forbidden cuando se intenta crear un Empleado sin autenticación")
    void createEmployee_WhenNoToken_ShouldReturn403Forbidden() throws Exception {
        mockMvc.perform(post("/users/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isForbidden());

        verify(userHandler, never()).createEmployee(any());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("Debe responder 403 Forbidden cuando el rol no es OWNER")
    void createEmployee_WhenRoleIsNotOwner_ShouldReturn403Forbidden() throws Exception {
        mockMvc.perform(post("/users/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isForbidden());

        verify(userHandler, never()).createEmployee(any());
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
        validDto.setIdentityDocument("100234ABC");

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());

        verify(userHandler, never()).createOwner(any());
    }

    @Test
    @DisplayName("Debe responder 400 Bad Request cuando el teléfono supera 13 caracteres")
    void createOwner_WhenPhoneIsTooLong_ShouldReturn400BadRequest() throws Exception {
        validDto.setPhoneNumber("+5730012345678999");

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());

        verify(userHandler, never()).createOwner(any());
    }

    @Test
    @DisplayName("Debe responder 201 Created cuando los datos de entrada para Cliente son válidos sin requerir token")
    void createClient_WhenValidData_ShouldReturn201Created() throws Exception {
        mockMvc.perform(post("/users/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        verify(userHandler).createClient(any(UserRequestDto.class));
    }

    @Test
    @DisplayName("Debe responder 400 Bad Request cuando faltan campos obligatorios para Cliente")
    void createClient_WhenMissingFields_ShouldReturn400BadRequest() throws Exception {
        validDto.setEmail(null);

        mockMvc.perform(post("/users/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());

        verify(userHandler, never()).createClient(any());
    }

    @Test
    @DisplayName("Debe responder 200 OK con UserResponseDto cuando se consulta por ID")
    void getUserById_WhenUserExists_ShouldReturn200Ok() throws Exception {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Valentina");
        responseDto.setEmail("valentina@correo.com");

        when(userHandler.getUserById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Valentina"))
                .andExpect(jsonPath("$.email").value("valentina@correo.com"));

        verify(userHandler).getUserById(1L);
    }

    @Test
    @DisplayName("Debe responder 404 Not Found cuando el usuario no existe")
    void getUserById_WhenUserNotFound_ShouldReturn404NotFound() throws Exception {
        when(userHandler.getUserById(99L)).thenThrow(new UserNotFoundException("User not found with id: 99"));

        mockMvc.perform(get("/users/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with id: 99"));

        verify(userHandler).getUserById(99L);
    }
}