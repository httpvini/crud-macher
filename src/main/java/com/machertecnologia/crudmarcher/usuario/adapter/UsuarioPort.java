package com.machertecnologia.crudmarcher.usuario.adapter;

import com.machertecnologia.crudmarcher.usuario.model.StatusRegistro;
import com.machertecnologia.crudmarcher.usuario.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuarioPort {
    List<Usuario> listarTodos();

    Optional<Usuario> buscarPorId(Long id);

    Usuario salvar(Usuario usuario);

    void inativarUsuario(Long id, StatusRegistro novoStatus, LocalDateTime dataRemocao, String usuarioRemocao);

    Usuario findByIdAndStatus(Long id, StatusRegistro statusRegistro);

    Optional<Usuario> findByCredencialUsuarioLogin(String login);
}
