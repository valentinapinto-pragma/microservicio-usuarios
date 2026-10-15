package com.example.users.application.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRequestDtoTest {

    @Test
    @DisplayName("Debe asignar y retornar todos los campos del DTO correctamente")
    void userRequestDto_ShouldSetAndGetFieldsCorrectly() {
        UserRequestDto dto = new UserRequestDto();
        LocalDate birthDate = LocalDate.of(1998, 5, 15);

        dto.setName("Valentina");
        dto.setLastName("Pinto");
        dto.setIdentityDocument("1002345678");
        dto.setPhoneNumber("+573001234567");
        dto.setBirthDate(birthDate);
        dto.setEmail("valentina@correo.com");
        dto.setPassword("Secret123");

        assertEquals("Valentina", dto.getName());
        assertEquals("Pinto", dto.getLastName());
        assertEquals("1002345678", dto.getIdentityDocument());
        assertEquals("+573001234567", dto.getPhoneNumber());
        assertEquals(birthDate, dto.getBirthDate());
        assertEquals("valentina@correo.com", dto.getEmail());
        assertEquals("Secret123", dto.getPassword());
    }
}
