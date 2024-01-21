package com.machertecnologia.crudmarcher.usuario.repository;

import com.machertecnologia.crudmarcher.usuario.model.StatusRegistro;
import com.machertecnologia.crudmarcher.usuario.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.status = :novoStatus, u.dataRemocao = :dataRemocao, u.usuarioRemocao = :usuarioRemocao WHERE u.id = :id")
    void inativarUsuario(Long id, StatusRegistro novoStatus, LocalDateTime dataRemocao, String usuarioRemocao);

    Usuario findByIdAndStatus(Long id, StatusRegistro statusRegistro);

    Optional<Usuario> findByCredencialUsuarioLogin(String login);
}

