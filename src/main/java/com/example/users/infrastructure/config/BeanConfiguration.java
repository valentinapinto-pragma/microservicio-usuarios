package com.example.users.infrastructure.config;

import com.example.users.domain.repositoryPort.PasswordEncoderPort;
import com.example.users.domain.repositoryPort.TokenServicePort;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import com.example.users.domain.servicePort.AuthServicePort;
import com.example.users.domain.servicePort.UserServicePort;
import com.example.users.domain.useCase.AuthUseCase;
import com.example.users.domain.useCase.UserUseCase;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserServicePort userServicePort(UserPersistentPort userRepository, PasswordEncoderPort passwordEncoderPort) {
        return new UserUseCase(userRepository, passwordEncoderPort);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Usuarios - Plazoleta de Comidas")
                        .version("1.0.0")
                        .description("API REST para la gestion de usuarios, roles y autenticacion.")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public AuthServicePort authServicePort(UserPersistentPort userPersistentPort,
                                           PasswordEncoderPort passwordEncoderPort,
                                           TokenServicePort tokenServicePort) {
        return new AuthUseCase(userPersistentPort, passwordEncoderPort, tokenServicePort);
    }
}