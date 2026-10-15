package com.example.users.infrastructure.persistence.adapter;

import com.example.users.domain.model.User;
import com.example.users.domain.repositoryPort.RolePersistentPort;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import com.example.users.infrastructure.mapper.MapperRoleEntity;
import com.example.users.infrastructure.mapper.MapperUserEntity;
import com.example.users.infrastructure.persistence.repository.RoleRepository;
import com.example.users.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserAdapterRepository implements UserPersistentPort {
    private final UserRepository userRepository;
    private final MapperUserEntity mapperUserEntity;

    @Override
    public User findById(Long aLong) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User save(User entity) {
        return mapperUserEntity.AToB(userRepository.save(mapperUserEntity.BToA(entity)));
    }


    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }
}
