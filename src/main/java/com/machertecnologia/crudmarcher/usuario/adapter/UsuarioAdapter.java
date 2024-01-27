package com.machertecnologia.crudmarcher.usuario.adapter;

import com.machertecnologia.crudmarcher.usuario.model.StatusRegistro;
import com.machertecnologia.crudmarcher.usuario.model.Usuario;
import com.machertecnologia.crudmarcher.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioAdapter implements UsuarioPort {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
    @Override
    public void inativarUsuario(Long id, StatusRegistro novoStatus, LocalDateTime dataRemocao, String usuarioRemocao) {
        usuarioRepository.inativarUsuario(id, novoStatus, dataRemocao, usuarioRemocao);
    }

    @Override
    public Usuario findByIdAndStatus(Long id, StatusRegistro statusRegistro) {
        return usuarioRepository.findByIdAndStatus(id, statusRegistro);
    }

    @Override
    public Optional<Usuario> findByCredencialUsuarioLogin(String login) {
        return usuarioRepository.findByCredencialUsuarioLogin(login);
    }

    @Override
    public Usuario findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }
}
