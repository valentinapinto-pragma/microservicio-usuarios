package com.example.users.infrastructure.persistence.adapter;

import com.example.users.domain.model.User;
import com.example.users.infrastructure.mapper.MapperUserEntity;
import com.example.users.infrastructure.persistence.entity.UserEntity;
import com.example.users.infrastructure.persistence.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAdapterRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapperUserEntity mapperUserEntity;

    @InjectMocks
    private UserAdapterRepository userAdapterRepository;

    @Test
    @DisplayName("Debe guardar un usuario mapeando a entidad y retornando el modelo")
    void save_ShouldPersistUserAndReturnDomainModel() {
        // Arrange
        User user = new User();
        user.setName("Valentina");

        UserEntity userEntity = new UserEntity();
        userEntity.setName("Valentina");

        // Usamos los nuevos métodos limpios: toEntity y toUser
        when(mapperUserEntity.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(mapperUserEntity.toUser(userEntity)).thenReturn(user);

        // Act
        User result = userAdapterRepository.save(user);

        // Assert
        assertNotNull(result);
        assertEquals("Valentina", result.getName());
        verify(mapperUserEntity).toEntity(user);
        verify(userRepository).save(userEntity);
        verify(mapperUserEntity).toUser(userEntity);
    }
}
