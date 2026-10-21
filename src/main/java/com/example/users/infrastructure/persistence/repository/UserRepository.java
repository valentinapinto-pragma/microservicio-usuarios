package com.example.users.infrastructure.persistence.repository;

import com.example.users.infrastructure.persistence.entity.RoleEntity;
import com.example.users.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);

}
