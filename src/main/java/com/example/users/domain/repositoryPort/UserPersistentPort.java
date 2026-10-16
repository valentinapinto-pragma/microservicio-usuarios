package com.example.users.domain.repositoryPort;

import com.example.users.domain.model.User;

import java.util.Optional;

public interface UserPersistentPort {
    User save(User user);
    Optional<User> findById(Long id);

}
