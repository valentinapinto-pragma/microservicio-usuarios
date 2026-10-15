package com.example.users.infrastructure.persistence.adapter;

import com.example.users.domain.model.User;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import com.example.users.infrastructure.mapper.MapperUserEntity;
import com.example.users.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAdapterRepository implements UserPersistentPort {

    private final UserRepository userRepository;
    private final MapperUserEntity mapperUserEntity;

    @Override
    public User save(User entity) {
        return mapperUserEntity.toUser(userRepository.save(mapperUserEntity.toEntity(entity)));
    }
}
