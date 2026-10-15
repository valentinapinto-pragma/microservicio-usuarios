package com.example.users.infrastructure.persistence.repository;

import com.example.users.infrastructure.persistence.entity.RoleEntity;
import com.example.users.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
