package com.example.users.domain.useCase;

import com.example.users.domain.exception.InvalidCredentialsException;
import com.example.users.domain.model.User;
import com.example.users.domain.repositoryPort.PasswordEncoderPort;
import com.example.users.domain.repositoryPort.TokenServicePort;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import com.example.users.domain.servicePort.AuthServicePort;

public class AuthUseCase implements AuthServicePort {

    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid email or password";

    private final UserPersistentPort userPersistentPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenServicePort tokenServicePort;

    public AuthUseCase(UserPersistentPort userPersistentPort,
                       PasswordEncoderPort passwordEncoderPort,
                       TokenServicePort tokenServicePort) {
        this.userPersistentPort = userPersistentPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenServicePort = tokenServicePort;
    }

    @Override
    public String login(String email, String password) {
        User user = userPersistentPort.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE));

        if (!passwordEncoderPort.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }

        return tokenServicePort.generateToken(user);
    }
}
