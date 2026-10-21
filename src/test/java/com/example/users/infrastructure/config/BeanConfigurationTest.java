package com.example.users.infrastructure.config;

import com.example.users.domain.repositoryPort.PasswordEncoderPort;
import com.example.users.domain.repositoryPort.TokenServicePort;
import com.example.users.domain.repositoryPort.UserPersistentPort;
import com.example.users.domain.servicePort.AuthServicePort;
import com.example.users.domain.servicePort.UserServicePort;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    private BeanConfiguration beanConfiguration;

    @Mock
    private UserPersistentPort userPersistentPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Mock
    private TokenServicePort tokenServicePort;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    @Test
    @DisplayName("Debe instanciar UserServicePort con sus dependencias")
    void userServicePort() {
        UserServicePort userServicePort = beanConfiguration.userServicePort(userPersistentPort, passwordEncoderPort);

        assertNotNull(userServicePort);
    }

    @Test
    @DisplayName("Debe instanciar AuthServicePort con sus dependencias")
    void authServicePort() {
        AuthServicePort authServicePort = beanConfiguration.authServicePort(
                userPersistentPort,
                passwordEncoderPort,
                tokenServicePort
        );

        assertNotNull(authServicePort);
    }

    @Test
    @DisplayName("Debe configurar y retornar la instancia de OpenAPI de Swagger")
    void customOpenAPI() {
        OpenAPI openAPI = beanConfiguration.customOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Microservicio de Usuarios - Plazoleta de Comidas", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
        assertEquals("API REST para la gestion de usuarios, roles y autenticacion.", openAPI.getInfo().getDescription());
        assertNotNull(openAPI.getInfo().getLicense());
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        assertEquals("http://springdoc.org", openAPI.getInfo().getLicense().getUrl());
    }
}
