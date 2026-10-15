package com.example.users.domain.repositoryPort;

import com.example.users.domain.model.User;

public interface UserPersistentPort {
    User save(User user);
}
