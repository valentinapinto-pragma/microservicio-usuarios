package com.example.users.infrastructure.persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserEntityTest {

    @Test
    @DisplayName("Debe construir la entidad correctamente con el Builder y Setters")
    void userEntity_ShouldSetAndGetFieldsCorrectly() {
        RoleEntity role = RoleEntity.builder()
                .id(2L)
                .name("ROLE_OWNER")
                .description("Propietario")
                .build();

        LocalDate birthDate = LocalDate.of(1995, 5, 20);

        // Prueba del Builder y constructor
        UserEntity user = UserEntity.builder()
                .id(1L)
                .name("Valentina")
                .lastName("Pinto")
                .identityDocument("1002345678")
                .phoneNumber("+573001234567")
                .birthDate(birthDate)
                .email("valentina@correo.com")
                .password("encodedPass")
                .role(role)
                .build();

        assertEquals(1L, user.getId());
        assertEquals("Valentina", user.getName());
        assertEquals("Pinto", user.getLastName());
        assertEquals("1002345678", user.getIdentityDocument());
        assertEquals("+573001234567", user.getPhoneNumber());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals("valentina@correo.com", user.getEmail());
        assertEquals("encodedPass", user.getPassword());
        assertEquals(role, user.getRole());

        // Constructor vacío y setters
        UserEntity emptyUser = new UserEntity();
        emptyUser.setName("Andrea");
        assertEquals("Andrea", emptyUser.getName());

        // Probar getters de RoleEntity
        assertEquals(2L, role.getId());
        assertEquals("ROLE_OWNER", role.getName());
        assertEquals("Propietario", role.getDescription());
    }
}
