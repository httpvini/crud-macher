package com.machertecnologia.crudmarcher.service;
import com.machertecnologia.crudmarcher.helper.MockObjectBuilder;
import com.machertecnologia.crudmarcher.usuario.DTO.AtualizacaoUsuarioDTO;
import com.machertecnologia.crudmarcher.usuario.adapter.CredencialUsuarioPort;
import com.machertecnologia.crudmarcher.usuario.model.CredencialUsuario;
import com.machertecnologia.crudmarcher.usuario.model.RoleUsuario;
import com.machertecnologia.crudmarcher.usuario.model.Usuario;
import com.machertecnologia.crudmarcher.usuario.service.AutorizacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorizacaoServiceTest {

    @Mock
    private CredencialUsuarioPort credencialUsuarioPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AutorizacaoService autorizacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        String username = "testUser";
        when(credencialUsuarioPort.getByLogin(username)).thenReturn(new CredencialUsuario());

        assertNotNull(autorizacaoService.loadUserByUsername(username));
    }

    @Test
    void testRegister() throws Exception {
        String login = "newUser";
        String password = "password123";
        RoleUsuario role = RoleUsuario.USER;

        when(credencialUsuarioPort.getByLogin(login)).thenReturn(null);
        when(passwordEncoder.encode(password)).thenReturn("hashedPassword");

        CredencialUsuario result = autorizacaoService.register(login, password, role);

        assertNotNull(result);
        assertEquals(login, result.getLogin());
        assertEquals("hashedPassword", result.getPassword());
        assertEquals(role, result.getRole());

        verify(credencialUsuarioPort, times(1)).salvar(result);
    }

    @Test
    void testRegisterUserAlreadyExists() {
        String login = "existingUser";

        when(credencialUsuarioPort.getByLogin(login)).thenReturn(new CredencialUsuario());

        assertThrows(Exception.class, () -> autorizacaoService.register(login, "password", RoleUsuario.USER));

        verify(credencialUsuarioPort, never()).salvar(any());
    }

    @Test
    void testGetAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertEquals(authentication, autorizacaoService.getAuthentication());
    }

    @Test
    void testUpdateAdmin() {
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList((GrantedAuthority) () -> "ROLE_ADMIN");

        when(authentication.getAuthorities()).thenAnswer(new Answer<Collection<? extends GrantedAuthority>>() {
            @Override
            public Collection<? extends GrantedAuthority> answer(InvocationOnMock invocation) throws Throwable {
                return authorities;
            }
        });

        AtualizacaoUsuarioDTO usuarioDTO = MockObjectBuilder.buildAtualizacaoUsuarioDTO("newLogin", "newPassword", RoleUsuario.USER);
        Usuario usuarioExistente = MockObjectBuilder.buildMockUsuario(new CredencialUsuario("oldLogin", "oldPassword", RoleUsuario.ADMIN));

        CredencialUsuario result = autorizacaoService.update(authentication, usuarioDTO, usuarioExistente);

        assertNotNull(result);
        assertEquals("newLogin", result.getLogin());
        assertEquals(RoleUsuario.USER, result.getRole());
    }

    @Test
    void testUpdateNonAdmin() {
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList((GrantedAuthority) () -> "ROLE_USER");

        when(authentication.getAuthorities()).thenAnswer(new Answer<Collection<? extends GrantedAuthority>>() {
            @Override
            public Collection<? extends GrantedAuthority> answer(InvocationOnMock invocation) throws Throwable {
                return authorities;
            }
        });

        AtualizacaoUsuarioDTO usuarioDTO = MockObjectBuilder.buildAtualizacaoUsuarioDTO("123456", RoleUsuario.USER);
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setCredencialUsuario(new CredencialUsuario("oldLogin", "oldPassword", RoleUsuario.ADMIN));

        CredencialUsuario result = autorizacaoService.update(authentication, usuarioDTO, usuarioExistente);

        assertNull(result);
    }

    @Test
    void testDelete() {
        CredencialUsuario credencialUsuario = new CredencialUsuario();
        autorizacaoService.delete(credencialUsuario);

        verify(credencialUsuarioPort, times(1)).delete(credencialUsuario);
    }

    @Test
    void testIsAdmin() {
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList((GrantedAuthority) () -> "ROLE_ADMIN");

        when(authentication.getAuthorities()).thenAnswer(new Answer<Collection<? extends GrantedAuthority>>() {
            @Override
            public Collection<? extends GrantedAuthority> answer(InvocationOnMock invocation) throws Throwable {
                return authorities;
            }
        });
        assertTrue(autorizacaoService.isAdmin(authentication));

        Collection<? extends GrantedAuthority> authoritiesUser = Collections.singletonList((GrantedAuthority) () -> "ROLE_USER");

        when(authentication.getAuthorities()).thenAnswer(new Answer<Collection<? extends GrantedAuthority>>() {
            @Override
            public Collection<? extends GrantedAuthority> answer(InvocationOnMock invocation) throws Throwable {
                return authoritiesUser;
            }
        });
        assertFalse(autorizacaoService.isAdmin(authentication));
    }
}