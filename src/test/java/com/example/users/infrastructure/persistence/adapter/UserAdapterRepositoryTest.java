package com.example.users.infrastructure.persistence.adapter;

import com.example.users.domain.model.User;
import com.example.users.infrastructure.mapper.MapperUserEntity;
import com.example.users.infrastructure.persistence.entity.UserEntity;
import com.example.users.infrastructure.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Valentina");
        user.setEmail("valentina@correo.com");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Valentina");
        userEntity.setEmail("valentina@correo.com");
    }

    @Test
    @DisplayName("Debe guardar un usuario mapeando a entidad y retornando el modelo")
    void save_ShouldPersistUserAndReturnDomainModel() {
        // Arrange
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

    @Test
    @DisplayName("Debe buscar usuario por ID correctamente")
    void findById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(mapperUserEntity.toUser(userEntity)).thenReturn(user);

        // Act
        Optional<User> result = userAdapterRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe buscar usuario por email correctamente")
    void findByEmail_ShouldReturnUser() {
        // Arrange
        String email = "valentina@correo.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(mapperUserEntity.toUser(userEntity)).thenReturn(user);

        // Act
        Optional<User> result = userAdapterRepository.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Debe listar todos los usuarios usando Streams")
    void findAll_ShouldReturnListOfUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.singletonList(userEntity));
        when(mapperUserEntity.toUser(userEntity)).thenReturn(user);

        // Act
        List<User> result = userAdapterRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Valentina", result.get(0).getName());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Debe actualizar un usuario mapeando a entidad y guardando")
    void update_ShouldSaveEntityViaRepository() {
        // Arrange
        when(mapperUserEntity.toEntity(user)).thenReturn(userEntity);

        // Act
        userAdapterRepository.update(user);

        // Assert
        verify(mapperUserEntity).toEntity(user);
        verify(userRepository).save(userEntity);
    }

    @Test
    @DisplayName("Debe eliminar un usuario mapeando a entidad y llamando a delete")
    void delete_ShouldDeleteEntityViaRepository() {
        // Arrange
        when(mapperUserEntity.toEntity(user)).thenReturn(userEntity);

        // Act
        userAdapterRepository.delete(user);

        // Assert
        verify(mapperUserEntity).toEntity(user);
        verify(userRepository).delete(userEntity);
    }
}
