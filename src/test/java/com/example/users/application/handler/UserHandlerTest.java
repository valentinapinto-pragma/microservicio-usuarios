package com.example.users.application.handler;

import com.example.users.application.dto.request.UserRequestDto;
import com.example.users.application.dto.response.UserResponseDto;
import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.User;
import com.example.users.domain.servicePort.UserServicePort;
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

    @Test
    @DisplayName("Debe consultar el usuario por id y mapearlo a UserResponseDto")
    void getUserById_ShouldCallServicePortAndReturnResponseDto() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("Valentina");

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(userId);
        responseDto.setName("Valentina");

        when(userServicePort.getUserById(userId)).thenReturn(user);
        when(userDtoMapper.toResponseDto(user)).thenReturn(responseDto);

        // Act
        UserResponseDto result = userHandler.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Valentina", result.getName());
        verify(userServicePort).getUserById(userId);
        verify(userDtoMapper).toResponseDto(user);
    }
}
