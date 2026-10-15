package com.example.users.infrastructure.mapper;

import com.example.users.domain.model.Role;
import com.example.users.domain.model.User;
import com.example.users.infrastructure.comun.MapperBase;
import com.example.users.infrastructure.persistence.entity.RoleEntity;
import com.example.users.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperUserEntity extends MapperBase<UserEntity, User> {
}
