package com.example.users.application.handler;

import com.example.users.application.dto.request.LoginRequestDto;
import com.example.users.application.dto.response.TokenResponseDto;
import com.example.users.domain.servicePort.AuthServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private AuthServicePort authServicePort;

    @InjectMocks
    private AuthHandler authHandler;

    @Test
    @DisplayName("Debe recibir el DTO, llamar al AuthServicePort y retornar TokenResponseDto")
    void login_ShouldCallServicePortAndReturnTokenResponseDto() {
        // Arrange
        LoginRequestDto requestDto = new LoginRequestDto("valentina@correo.com", "PasswordSeguro123");
        String expectedToken = "jwt.token.valido";

        when(authServicePort.login("valentina@correo.com", "PasswordSeguro123"))
                .thenReturn(expectedToken);

        // Act
        TokenResponseDto responseDto = authHandler.login(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(expectedToken, responseDto.getToken());
        verify(authServicePort).login("valentina@correo.com", "PasswordSeguro123");
    }
}
