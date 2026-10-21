package com.example.users.domain.useCase;

import com.example.users.domain.exception.UserNotFoundException;
import com.example.users.domain.exception.UserUnderAgeException;
import com.example.users.domain.model.User;
import com.example.users.domain.repositoryPort.PasswordEncoderPort;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserPersistentPort userPersistentPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Valentina");
        user.setLastName("Pinto");
        user.setIdentityDocument("1002345678");
        user.setPhoneNumber("+573001234567");
        user.setEmail("valentina@correo.com");
        user.setPassword("ClavePlana123");
    }

    @Test
    @DisplayName("Debe crear el propietario exitosamente cuando es mayor de edad")
    void createOwner_WhenUserIsMajor_ShouldCreateSuccessfully() {
        // Arrange (Preparar datos: 25 años)
        user.setBirthDate(LocalDate.now().minusYears(25));
        when(passwordEncoderPort.encode("ClavePlana123")).thenReturn("$2a$10$ClaveEncriptadaFake");

        // Act (Ejecutar)
        userUseCase.createOwner(user);

        // Assert (Verificar resultados)
        verify(passwordEncoderPort).encode("ClavePlana123");
        verify(userPersistentPort).save(user);
        assertEquals("$2a$10$ClaveEncriptadaFake", user.getPassword());
        assertNotNull(user.getRole());
        assertEquals(2L, user.getRole().getId());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el usuario es menor de edad")
    void createOwner_WhenUserIsUnderAge_ShouldThrowException() {
        // Arrange (Preparar datos: 15 años)
        user.setBirthDate(LocalDate.now().minusYears(15));

        // Act & Assert (Verificar que lance la excepción)
        assertThrows(UserUnderAgeException.class, () -> userUseCase.createOwner(user));

        // Verificar que NUNCA intentó guardar en la base de datos
        verify(userPersistentPort, never()).save(any());
        verify(passwordEncoderPort, never()).encode(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la fecha de nacimiento es nula")
    void createOwner_WhenBirthDateIsNull_ShouldThrowException() {
        // Arrange
        user.setBirthDate(null);

        // Act & Assert
        assertThrows(UserUnderAgeException.class, () -> userUseCase.createOwner(user));
        verify(userPersistentPort, never()).save(any());
    }
    @Test
    @DisplayName("Debe lanzar UserNotFoundException cuando el id no existe")
    void getUserById_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        when(userPersistentPort.findById(99L)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userUseCase.getUserById(99L));
        verify(userPersistentPort).findById(99L);
    }
}
