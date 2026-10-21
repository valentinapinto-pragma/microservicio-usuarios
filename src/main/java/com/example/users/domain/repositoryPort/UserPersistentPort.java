package com.example.users.domain.repositoryPort;

import com.example.users.domain.comun.CrudBase;
import com.example.users.domain.model.User;

import java.util.Optional;

public interface UserPersistentPort extends CrudBase<User, Long> {
    Optional<User> findByEmail(String email);
}
