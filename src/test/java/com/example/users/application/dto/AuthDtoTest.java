package com.example.users.application.dto;

import com.example.users.application.dto.request.LoginRequestDto;
import com.example.users.application.dto.response.RoleResponseDto;
import com.example.users.application.dto.response.TokenResponseDto;
import com.example.users.application.dto.response.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthDtoTest {

    @Test
    @DisplayName("Debe asignar y retornar campos de LoginRequestDto")
    void loginRequestDto_ShouldWorkCorrectly() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("valentina@correo.com");
        dto.setPassword("Secret123");

        assertEquals("valentina@correo.com", dto.getEmail());
        assertEquals("Secret123", dto.getPassword());
    }

    @Test
    @DisplayName("Debe asignar y retornar campos de TokenResponseDto")
    void tokenResponseDto_ShouldWorkCorrectly() {
        TokenResponseDto dto = new TokenResponseDto("token123");
        dto.setToken("newToken");

        assertEquals("newToken", dto.getToken());
    }

    @Test
    @DisplayName("Debe cubrir UserResponseDto y RoleResponseDto con getters y setters")
    void responseDtos_ShouldWorkCorrectly() {
        RoleResponseDto roleDto = new RoleResponseDto();
        roleDto.setId(2L);
        roleDto.setName("ROLE_OWNER");
        roleDto.setDescription("Propietario");

        assertEquals(2L, roleDto.getId());
        assertEquals("ROLE_OWNER", roleDto.getName());
        assertEquals("Propietario", roleDto.getDescription());

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(1L);
        userDto.setName("Valentina");
        userDto.setLastName("Pinto");
        userDto.setIdentityDocument("1002345678");
        userDto.setPhoneNumber("+573001234567");
        userDto.setEmail("valentina@correo.com");
        userDto.setRole(roleDto);

        assertEquals(1L, userDto.getId());
        assertEquals("Valentina", userDto.getName());
        assertEquals("Pinto", userDto.getLastName());
        assertEquals("1002345678", userDto.getIdentityDocument());
        assertEquals("+573001234567", userDto.getPhoneNumber());
        assertEquals("valentina@correo.com", userDto.getEmail());
        assertEquals(roleDto, userDto.getRole());
    }
}
