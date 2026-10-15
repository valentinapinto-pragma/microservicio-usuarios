package com.example.users.application.handler;

import com.example.users.application.dto.request.UserRequestDto;
import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.User;
import com.example.users.domain.servicePort.UserServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private UserServicePort userServicePort;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserHandler userHandler;

    @Test
    @DisplayName("Debe mapear el DTO a Modelo y llamar al puerto de servicio")
    void createOwner_ShouldMapDtoAndCallServicePort() {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("Valentina");
        requestDto.setEmail("test@correo.com");

        User user = new User();
        user.setName("Valentina");
        user.setEmail("test@correo.com");

        when(userDtoMapper.toUser(requestDto)).thenReturn(user);

        // Act
        userHandler.createOwner(requestDto);

        // Assert
        verify(userDtoMapper).toUser(requestDto);
        verify(userServicePort).createOwner(user);
    }
}
