package com.example.users.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    @DisplayName("Debe instanciar y asignar todos los atributos correctamente con el constructor y setters")
    void user_ShouldSetAndGetAllFieldsCorrectly() {
        Role role = new Role();
        role.setId(2L);
        role.setName("ROLE_OWNER");
        role.setDescription("Propietario");

        LocalDate birthDate = LocalDate.of(1995, 5, 20);

        // Constructor con todos los campos
        User user = new User(1L, "Valentina", "Pinto", "1002345678",
                "+573001234567", birthDate, "valentina@correo.com", "pass123", role);

        assertEquals(1L, user.getId());
        assertEquals("Valentina", user.getName());
        assertEquals("Pinto", user.getLastName());
        assertEquals("1002345678", user.getIdentityDocument());
        assertEquals("+573001234567", user.getPhoneNumber());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals("valentina@correo.com", user.getEmail());
        assertEquals("pass123", user.getPassword());
        assertEquals(role, user.getRole());

        // Modificación con setters
        user.setId(2L);
        user.setName("Andrea");
        user.setLastName("Gomez");
        user.setIdentityDocument("987654321");
        user.setPhoneNumber("+573111111111");
        user.setEmail("andrea@correo.com");
        user.setPassword("newPass");

        assertEquals(2L, user.getId());
        assertEquals("Andrea", user.getName());
        assertEquals("Gomez", user.getLastName());
        assertEquals("987654321", user.getIdentityDocument());
        assertEquals("+573111111111", user.getPhoneNumber());
        assertEquals("andrea@correo.com", user.getEmail());
        assertEquals("newPass", user.getPassword());
    }
}
