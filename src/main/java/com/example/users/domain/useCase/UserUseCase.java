package com.example.users.domain.useCase;

import com.example.users.domain.exception.UserUnderAgeException;
import com.example.users.domain.model.Role;
import com.example.users.domain.model.User;
import com.example.users.domain.repositoryPort.PasswordEncoderPort;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import com.example.users.domain.servicePort.UserServicePort;
import java.time.LocalDate;
import java.time.Period;

import java.time.LocalDate;

public class UserUseCase implements UserServicePort {
    // identificador 1=Admin, 2=Propietario, 3=Empleado, 4=Cliente
    private static final Long OWNER_ROLE_ID = 2L;

    private final UserPersistentPort userPersistentPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UserUseCase(UserPersistentPort userPersistentPort, PasswordEncoderPort passwordEncoderPort) {
        this.userPersistentPort = userPersistentPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void createOwner(User user) {

        validateAge(user.getBirthDate());

        user.setPassword(passwordEncoderPort.encode(user.getPassword()));

        Role role = new Role();
        role.setId(OWNER_ROLE_ID);
        user.setRole(role);

        userPersistentPort.save(user);
    }
    private void validateAge(LocalDate birthDate) {
        if (birthDate == null || Period.between(birthDate, LocalDate.now()).getYears() < 18) {
            throw new UserUnderAgeException();
        }
    }


}
