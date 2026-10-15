package com.example.users.application.mapper;

import com.example.users.application.dto.request.UserRequestDto;
import com.example.users.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDtoMapperTest {

    private final UserDtoMapper mapper = Mappers.getMapper(UserDtoMapper.class);

    @Test
    @DisplayName("Debe mapear UserRequestDto a User correctamente usando MapStruct")
    void toUser_ShouldMapDtoToDomainModel() {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Valentina");
        dto.setLastName("Pinto");
        dto.setIdentityDocument("1002345678");
        dto.setPhoneNumber("+573001234567");
        dto.setBirthDate(LocalDate.of(1998, 5, 15));
        dto.setEmail("valentina@correo.com");
        dto.setPassword("Secret123");

        User user = mapper.toUser(dto);

        assertNotNull(user);
        assertEquals("Valentina", user.getName());
        assertEquals("Pinto", user.getLastName());
        assertEquals("1002345678", user.getIdentityDocument());
        assertEquals("+573001234567", user.getPhoneNumber());
        assertEquals(LocalDate.of(1998, 5, 15), user.getBirthDate());
        assertEquals("valentina@correo.com", user.getEmail());
        assertEquals("Secret123", user.getPassword());
    }

    @Test
    @DisplayName("Debe retornar null si el DTO es null")
    void toUser_WhenDtoIsNull_ShouldReturnNull() {
        assertNull(mapper.toUser(null));
    }
}
