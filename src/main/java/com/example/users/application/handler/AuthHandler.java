package com.example.users.application.handler;

import com.example.users.application.dto.request.LoginRequestDto;
import com.example.users.application.dto.response.TokenResponseDto;
import com.example.users.domain.servicePort.AuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler {

    private final AuthServicePort authServicePort;

    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        String token = authServicePort.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return new TokenResponseDto(token);
    }
}
