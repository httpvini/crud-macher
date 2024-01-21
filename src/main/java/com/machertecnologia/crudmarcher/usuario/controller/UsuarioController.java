package com.machertecnologia.crudmarcher.usuario.controller;

import com.machertecnologia.crudmarcher.usuario.DTO.AtualizacaoUsuarioDTO;
import com.machertecnologia.crudmarcher.usuario.DTO.CriarUsuarioDTO;
import com.machertecnologia.crudmarcher.usuario.DTO.UsuarioDTO;
import com.machertecnologia.crudmarcher.usuario.model.StatusRegistro;
import com.machertecnologia.crudmarcher.usuario.model.Usuario;
import com.machertecnologia.crudmarcher.usuario.service.AutorizacaoService;
import com.machertecnologia.crudmarcher.usuario.service.UsuarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @GetMapping
    public ResponseEntity listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(listaUsuariosMapper(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuarioResponse = usuarioService.buscarPorId(id).orElse(null);
        Map<Long, UsuarioDTO> usuarioDTO = listaUsuariosMapper(Arrays.asList(usuarioResponse));
        return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody CriarUsuarioDTO usuario) {
        try {
            Usuario usuarioResponse = usuarioService.criarUsuario(
                    usuario.cpf(),
                    usuario.nome(),
                    usuario.dataNascimento(),
                    usuario.endereco(),
                    usuario.login(),
                    usuario.password(),
                    usuario.role()
            );
            Map<Long, UsuarioDTO> usuarioDTO = listaUsuariosMapper(Arrays.asList(usuarioResponse));
            return ResponseEntity.ok(usuarioDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuarioService.inativarUsuario(usuario.getId());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtualizacaoUsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizacaoUsuarioDTO usuarioDTO) {
        try {
            usuarioService.atualizarUsuario(usuarioDTO, id);
            return ResponseEntity.ok(usuarioDTO);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível atualizar o usuário");
        }
    }

    private Map<Long, UsuarioDTO> listaUsuariosMapper(List<Usuario> usuarios) {
        Map<Long, UsuarioDTO> usuariosDTO = usuarios.stream()
                .collect(Collectors.toMap(
                        Usuario::getId,
                        usuario -> new UsuarioDTO(
                                usuario.getId(),
                                usuario.getCpf(),
                                usuario.getNome(),
                                usuario.getDataNascimento(),
                                usuario.getEndereco(),
                                usuario.getStatus(),
                                usuario.getDataCriacao(),
                                usuario.getUsuarioCriacao(),
                                usuario.getDataAtualizacao(),
                                usuario.getUsuarioAtualizacao(),
                                usuario.getDataRemocao(),
                                usuario.getUsuarioRemocao(),
                                usuario.getCredencialUsuario().getLogin(),
                                usuario.getCredencialUsuario().getRole()
                        )
                ));
        return usuariosDTO;
    }
}
