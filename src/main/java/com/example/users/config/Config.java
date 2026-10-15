package com.example.users.config;

import com.example.users.domain.repositoryPort.PasswordEncoderPort;
import com.example.users.domain.repositoryPort.RolePersistentPort;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import com.example.users.domain.servicePort.RoleServicePort;
import com.example.users.domain.servicePort.UserServicePort;
import com.example.users.domain.useCase.RoleUseCase;
import com.example.users.domain.useCase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public RoleServicePort RoleServicePort(RolePersistentPort roleRepository){
        return new RoleUseCase(roleRepository);
    }
    @Bean
    public UserServicePort UserServicePort(UserPersistentPort userRepository, PasswordEncoderPort passwordEncoderPort){
        return new UserUseCase(userRepository, passwordEncoderPort);
    }

}
