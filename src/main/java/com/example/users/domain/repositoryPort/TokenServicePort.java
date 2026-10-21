package com.example.users.domain.repositoryPort;

import com.example.users.domain.model.User;

public interface TokenServicePort {
    String generateToken(User user);
}
