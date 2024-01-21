package com.machertecnologia.crudmarcher.usuario.adapter;

import com.machertecnologia.crudmarcher.usuario.model.CredencialUsuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface CredencialUsuarioPort {
    UserDetails getByLogin(String login);
    void salvar(CredencialUsuario credencialUsuario);
    void delete(CredencialUsuario credencialUsuario);
}
