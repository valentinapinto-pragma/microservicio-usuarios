package com.example.users.infrastructure.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BcryptAdapterTest {

    private final BcryptAdapter bcryptAdapter = new BcryptAdapter();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("Debe encriptar la contraseña en texto plano correctamente con BCrypt")
    void encode_ShouldHashPasswordWithBcrypt() {
        // Arrange
        String rawPassword = "PasswordSeguro123";

        // Act
        String encodedPassword = bcryptAdapter.encode(rawPassword);

        // Assert
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(encodedPassword.startsWith("$2a$"));
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword)); // Valida que BCrypt la reconozca
    }
}
