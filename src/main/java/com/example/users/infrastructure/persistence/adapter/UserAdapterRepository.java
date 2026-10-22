package com.example.users.infrastructure.persistence.adapter;

import com.example.users.domain.model.User;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import com.example.users.infrastructure.mapper.MapperUserEntity;
import com.example.users.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserAdapterRepository implements UserPersistentPort {

    private final UserRepository userRepository;
    private final MapperUserEntity mapperUserEntity;

    @Override
    public User save(User entity) {
        return mapperUserEntity.toUser(userRepository.save(mapperUserEntity.toEntity(entity)));
    }
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(mapperUserEntity::toUser);
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(mapperUserEntity::toUser);
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(mapperUserEntity::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public void update(User entity) {
        userRepository.save(mapperUserEntity.toEntity(entity));
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(mapperUserEntity.toEntity(entity));
    }



}
