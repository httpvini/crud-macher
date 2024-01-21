package com.machertecnologia.crudmarcher.service;

import com.machertecnologia.crudmarcher.usuario.model.CredencialUsuario;
import com.machertecnologia.crudmarcher.usuario.service.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TokenServiceTest {

    @Mock
    private CredencialUsuario credencialUsuario;

    @InjectMocks
    private TokenService tokenService;

    @Test
    void testGenerateToken() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "testSecret");
        when(credencialUsuario.getLogin()).thenReturn("testUser");

        String token = tokenService.generateToken(credencialUsuario);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testValidateTokenValid() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "testSecret");
        when(credencialUsuario.getLogin()).thenReturn("testUser");

        String token = tokenService.generateToken(credencialUsuario);

        String subject = tokenService.validateToken(token);

        assertEquals("testUser", subject);
    }

    @Test
    void testValidateTokenInvalid() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "invalidSecret");
        when(credencialUsuario.getLogin()).thenReturn("");

        String token = tokenService.generateToken(credencialUsuario);

        String subject = tokenService.validateToken(token);

        assertEquals("", subject);
    }

    @Test
    void testGenExpirationDate() {
        MockitoAnnotations.initMocks(this);

        Instant expirationDate = ReflectionTestUtils.invokeMethod(tokenService, "genExpirationDate");

        assertNotNull(expirationDate);
    }
}
