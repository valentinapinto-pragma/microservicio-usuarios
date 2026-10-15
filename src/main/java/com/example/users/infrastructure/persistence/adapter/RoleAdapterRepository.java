package com.example.users.infrastructure.persistence.adapter;

import com.example.users.domain.model.Role;
import com.example.users.domain.repositoryPort.RolePersistentPort;
import com.example.users.infrastructure.mapper.MapperRoleEntity;
import com.example.users.infrastructure.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoleAdapterRepository implements RolePersistentPort {
    private final RoleRepository roleRepository;
    private final MapperRoleEntity mapperRoleEntity;
    @Override
    public Role findById(Long aLong) {
        return mapperRoleEntity.AToB(roleRepository.findById(aLong).get());
    }

    @Override
    public List<Role> findAll() {
        return mapperRoleEntity.AToB(roleRepository.findAll().stream()).collect(Collectors.toList());
    }

    @Override
    public Role save(Role entity) {

        return mapperRoleEntity.AToB(roleRepository.save(mapperRoleEntity.BToA(entity)));
    }

    @Override
    public void update(Role entity) {
        roleRepository.save(mapperRoleEntity.BToA(entity));
    }

    @Override
    public void delete(Role entity) {
        roleRepository.delete(mapperRoleEntity.BToA(entity));
    }
}
