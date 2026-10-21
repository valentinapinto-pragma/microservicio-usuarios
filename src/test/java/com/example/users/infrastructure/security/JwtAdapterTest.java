package com.example.users.infrastructure.security;

import com.example.users.domain.model.Role;
import com.example.users.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtAdapterTest {

    private static final String SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final Long EXPIRATION = 3600000L;

    private JwtAdapter jwtAdapter;

    @BeforeEach
    void setUp() {
        jwtAdapter = new JwtAdapter(SECRET, EXPIRATION);
    }

    @Test
    @DisplayName("Debe generar token JWT válido con claims de id y role")
    void generateToken_ShouldReturnValidToken() {
        Role role = new Role();
        role.setId(2L);
        role.setName("ROLE_OWNER");

        User user = new User();
        user.setId(10L);
        user.setEmail("valentina@correo.com");
        user.setRole(role);

        String token = jwtAdapter.generateToken(user);

        assertNotNull(token);
        assertTrue(token.startsWith("ey"));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("valentina@correo.com", claims.getSubject());
        assertEquals(10, claims.get("id", Integer.class));
        assertEquals("ROLE_OWNER", claims.get("role", String.class));
    }

    @Test
    @DisplayName("Debe generar token con rol vacío si es nulo")
    void generateToken_WhenRoleIsNull_ShouldWork() {
        User user = new User();
        user.setId(5L);
        user.setEmail("sinrol@correo.com");
        user.setRole(null);

        String token = jwtAdapter.generateToken(user);
        assertNotNull(token);
    }
}
