package com.example.users.domain.useCase;

import com.example.users.domain.exception.InvalidCredentialsException;
import com.example.users.domain.model.Role;
import com.example.users.domain.model.User;
import com.example.users.domain.repositoryPort.PasswordEncoderPort;
import com.example.users.domain.repositoryPort.TokenServicePort;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private UserPersistentPort userPersistentPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Mock
    private TokenServicePort tokenServicePort;

    @InjectMocks
    private AuthUseCase authUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(2L);
        role.setName("ROLE_OWNER");

        user = new User();
        user.setId(1L);
        user.setEmail("valentina@correo.com");
        user.setPassword("$2a$10$hashedPassword123");
        user.setRole(role);
    }

    @Test
    @DisplayName("Debe autenticar y retornar el token cuando las credenciales son válidas")
    void login_WhenCredentialsAreValid_ShouldReturnToken() {
        String rawPassword = "PasswordSeguro123";
        String expectedToken = "jwt.token.valido";

        when(userPersistentPort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches(rawPassword, user.getPassword())).thenReturn(true);
        when(tokenServicePort.generateToken(user)).thenReturn(expectedToken);

        String resultToken = authUseCase.login(user.getEmail(), rawPassword);

        assertNotNull(resultToken);
        assertEquals(expectedToken, resultToken);
        verify(userPersistentPort).findByEmail(user.getEmail());
        verify(passwordEncoderPort).matches(rawPassword, user.getPassword());
        verify(tokenServicePort).generateToken(user);
    }

    @Test
    @DisplayName("Debe lanzar InvalidCredentialsException cuando el usuario no existe")
    void login_WhenUserNotFound_ShouldThrowInvalidCredentialsException() {
        String email = "noexiste@correo.com";
        String rawPassword = "PasswordSeguro123";

        when(userPersistentPort.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> authUseCase.login(email, rawPassword));
        verifyNoInteractions(passwordEncoderPort);
        verifyNoInteractions(tokenServicePort);
    }

    @Test
    @DisplayName("Debe lanzar InvalidCredentialsException cuando la contraseña es incorrecta")
    void login_WhenPasswordDoesNotMatch_ShouldThrowInvalidCredentialsException() {
        String wrongPassword = "ClaveEquivocada";

        when(userPersistentPort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches(wrongPassword, user.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authUseCase.login(user.getEmail(), wrongPassword));
        verifyNoInteractions(tokenServicePort);
    }
}
