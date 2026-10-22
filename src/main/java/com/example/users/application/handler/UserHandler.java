package com.example.users.application.handler;

import com.example.users.application.dto.request.UserRequestDto;
import com.example.users.application.dto.response.UserResponseDto;
import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.User;
import com.example.users.domain.servicePort.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler {

    private final UserServicePort userServicePort;
    private final UserDtoMapper userDtoMapper;

    public void createOwner(UserRequestDto userRequestDto) {
        User user = userDtoMapper.toUser(userRequestDto);
        userServicePort.createOwner(user);
    }

    public void createEmployee(UserRequestDto userRequestDto) {
        User user = userDtoMapper.toUser(userRequestDto);
        userServicePort.createEmployee(user);
    }

    public UserResponseDto getUserById(Long id) {
        return userDtoMapper.toResponseDto(userServicePort.getUserById(id));
    }
}
