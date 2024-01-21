package com.machertecnologia.crudmarcher.usuario.repository;

import com.machertecnologia.crudmarcher.usuario.model.CredencialUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface CredencialUsuarioRepository extends JpaRepository<CredencialUsuario, Long> {
    UserDetails findBylogin(String login);
}
