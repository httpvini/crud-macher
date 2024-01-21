package com.machertecnologia.crudmarcher.usuario.adapter;

import com.machertecnologia.crudmarcher.usuario.model.CredencialUsuario;
import com.machertecnologia.crudmarcher.usuario.repository.CredencialUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CredencialUsuarioRepositoryAdapter implements CredencialUsuarioPort {
    @Autowired
    CredencialUsuarioRepository repository;

    @Override
    public UserDetails getByLogin(String login) {
        return repository.findBylogin(login);
    }
    @Override
    public void salvar(CredencialUsuario credencialUsuario){
        repository.save(credencialUsuario);
    }

    @Override
    public void delete(CredencialUsuario credencialUsuario) {
        repository.delete(credencialUsuario);
    }
}
