package com.machertecnologia.crudmarcher.service;

import com.machertecnologia.crudmarcher.helper.MockObjectBuilder;
import com.machertecnologia.crudmarcher.usuario.DTO.AtualizacaoUsuarioDTO;
import com.machertecnologia.crudmarcher.usuario.adapter.CredencialUsuarioPort;
import com.machertecnologia.crudmarcher.usuario.adapter.UsuarioPort;
import com.machertecnologia.crudmarcher.usuario.model.RoleUsuario;
import com.machertecnologia.crudmarcher.usuario.model.StatusRegistro;
import com.machertecnologia.crudmarcher.usuario.model.Usuario;
import com.machertecnologia.crudmarcher.usuario.service.AutorizacaoService;
import com.machertecnologia.crudmarcher.usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    @Mock
    private UsuarioPort usuarioPort;

    @Mock
    private AutorizacaoService autorizacaoService;

    @Mock
    private CredencialUsuarioPort credencialUsuarioPort;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListarTodos() {
        MockitoAnnotations.initMocks(this);

        List<Usuario> usuarios = Arrays.asList(
                MockObjectBuilder.buildMockUsuario(),
                MockObjectBuilder.buildMockUsuario()
        );

        when(usuarioPort.listarTodos()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.listarTodos();

        assertEquals(2, result.size());
    }

    @Test
    void testBuscarPorId() {
        MockitoAnnotations.initMocks(this);

        Long usuarioId = 1L;
        Usuario usuario = MockObjectBuilder.buildMockUsuario();
        when(usuarioPort.buscarPorId(usuarioId)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.buscarPorId(usuarioId);

        assertEquals(usuario, result.orElse(null));
    }

    @Test
    void testInativarUsuario() {
        Long usuarioId = 1L;
        String usuarioName = "usuario123";
        Authentication authentication = MockObjectBuilder.buildMockAuthentication();

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioId);
        usuarioExistente.setStatus(StatusRegistro.ATIVO);

        when(autorizacaoService.getAuthentication()).thenReturn(authentication);

        when(authentication.getName()).thenReturn(usuarioName);

        when(usuarioPort.findByIdAndStatus(usuarioId, StatusRegistro.ATIVO)).thenReturn(usuarioExistente);

        usuarioService.inativarUsuario(usuarioId);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<StatusRegistro> statusCaptor = ArgumentCaptor.forClass(StatusRegistro.class);
        ArgumentCaptor<LocalDateTime> dateTimeCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);

        verify(usuarioPort).inativarUsuario(
                idCaptor.capture(),
                statusCaptor.capture(),
                dateTimeCaptor.capture(),
                nameCaptor.capture()
        );

        assertEquals(usuarioId, idCaptor.getValue());
        assertEquals(StatusRegistro.REMOVIDO, statusCaptor.getValue());
        assertNotNull(dateTimeCaptor.getValue()); // Não valida o valor específico de LocalDateTime
        assertEquals(usuarioName, nameCaptor.getValue());
    }


    @Test
    void testAtualizarUsuario() throws Exception {
        MockitoAnnotations.initMocks(this);

        Long usuarioId = 1L;
        AtualizacaoUsuarioDTO atualizacaoUsuarioDTO = MockObjectBuilder.buildAtualizacaoUsuarioDTO("123456", RoleUsuario.USER);
        Usuario usuarioExistente = MockObjectBuilder.buildMockUsuario();

        when(usuarioPort.buscarPorId(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(autorizacaoService.getAuthentication()).thenReturn(MockObjectBuilder.buildMockAuthentication());

        usuarioService.atualizarUsuario(atualizacaoUsuarioDTO, usuarioId);

        verify(usuarioPort, times(1)).salvar(any(Usuario.class));
    }
}
