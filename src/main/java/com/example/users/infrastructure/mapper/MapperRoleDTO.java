package com.example.users.infrastructure.mapper;

import com.example.users.domain.model.Role;
import com.example.users.infrastructure.comun.MapperBase;
import com.example.users.infrastructure.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperRoleDTO extends MapperBase<RoleDTO, Role> {

}
