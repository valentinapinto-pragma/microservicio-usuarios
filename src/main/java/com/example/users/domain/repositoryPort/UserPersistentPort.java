package com.example.users.domain.repositoryPort;

import com.example.users.domain.model.User;

public interface UserPersistencePort {
    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByIdentityDocument(String identityDocument);

}
