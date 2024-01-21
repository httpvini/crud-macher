package com.machertecnologia.crudmarcher.usuario.service;

import com.machertecnologia.crudmarcher.usuario.DTO.AtualizacaoUsuarioDTO;
import com.machertecnologia.crudmarcher.usuario.adapter.CredencialUsuarioPort;
import com.machertecnologia.crudmarcher.usuario.model.*;
import com.machertecnologia.crudmarcher.usuario.adapter.UsuarioPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioPort usuarioAdapter;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private CredencialUsuarioPort credencialUsuarioAdapter;

    public List<Usuario> listarTodos() {
        return usuarioAdapter.listarTodos();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioAdapter.buscarPorId(id);
    }

    public Usuario buscaPorIdEStatus(Long id, StatusRegistro statusRegistro) {
        return usuarioAdapter.findByIdAndStatus(id, statusRegistro);
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        return usuarioAdapter.findByCredencialUsuarioLogin(login);
    }

    public Usuario criarUsuario(String cpf, String nome, LocalDate dataNascimento, Endereco endereco, String login, String password, RoleUsuario role) throws Exception {

        Authentication authentication = autorizacaoService.getAuthentication();

        Usuario novoUsuario = new Usuario();
        novoUsuario.setCpf(cpf);
        novoUsuario.setNome(nome);
        novoUsuario.setDataNascimento(dataNascimento);
        novoUsuario.setEndereco(endereco);
        novoUsuario.setStatus(StatusRegistro.ATIVO);
        novoUsuario.setUsuarioCriacao(authentication.getName());
        novoUsuario.setDataCriacao(LocalDateTime.now());
        usuarioAdapter.salvar(novoUsuario);

        //Caso não sejam passados os dados de credenciais na criação do usuário, o login e senha padrão serão o cpf e role padrão será USER.
        CredencialUsuario credencialUsuario = autorizacaoService.register(
                Objects.requireNonNullElse(login, cpf),
                Objects.requireNonNullElse(password,cpf),
                Objects.requireNonNullElse(role, RoleUsuario.USER)
        );

        credencialUsuarioAdapter.salvar(credencialUsuario);
        novoUsuario.setCredencialUsuario(credencialUsuario);
        usuarioAdapter.salvar(novoUsuario);

        return novoUsuario;
    }

    public void inativarUsuario(Long id) {
        Usuario usuarioExistente = usuarioAdapter.findByIdAndStatus(id, StatusRegistro.ATIVO);
        Authentication authentication = autorizacaoService.getAuthentication();
        if (usuarioExistente != null) {
            usuarioAdapter.inativarUsuario(
                    id,
                    StatusRegistro.REMOVIDO,
                    LocalDateTime.now(),
                    authentication.getName()
            );
        }
    }

    public void atualizarUsuario(AtualizacaoUsuarioDTO usuario, Long id) throws Exception {
        Authentication authentication = autorizacaoService.getAuthentication();
        Optional<Usuario> usuarioOpt = usuarioAdapter.buscarPorId(id);

        if (usuarioOpt.isPresent() && usuarioOpt.get().getStatus() == StatusRegistro.ATIVO) {
            Usuario usuarioExistente = usuarioOpt.get();

            usuarioExistente.setNome(Objects.requireNonNullElse(usuario.cpf(), usuarioExistente.getCpf()));
            usuarioExistente.setNome(Objects.requireNonNullElse(usuario.nome(), usuarioExistente.getNome()));
            usuarioExistente.setDataNascimento(Objects.requireNonNullElse(usuario.nascimento(), usuarioExistente.getDataNascimento()));
            usuarioExistente.setEndereco(Objects.requireNonNullElse(usuario.endereco(), usuarioExistente.getEndereco()));
            usuarioExistente.setUsuarioAtualizacao(authentication.getName());
            usuarioExistente.setDataAtualizacao(LocalDateTime.now());

            CredencialUsuario credencialUsuario =  autorizacaoService.update(authentication, usuario, usuarioExistente);
            usuarioExistente.setCredencialUsuario(credencialUsuario);
            usuarioAdapter.salvar(usuarioExistente);
        } else {
            throw new Exception("Não foi possível atualizar os dados do usuário");
        }
    }


}
