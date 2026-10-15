package com.example.users.infrastructure.mapper;

import com.example.users.domain.model.User;
import com.example.users.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperUserEntity {

    User toUser(UserEntity userEntity);

    UserEntity toEntity(User user);
}
