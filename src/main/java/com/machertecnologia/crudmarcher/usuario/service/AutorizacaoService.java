package com.machertecnologia.crudmarcher.usuario.service;

import com.machertecnologia.crudmarcher.usuario.DTO.AtualizacaoUsuarioDTO;
import com.machertecnologia.crudmarcher.usuario.adapter.CredencialUsuarioPort;
import com.machertecnologia.crudmarcher.usuario.model.CredencialUsuario;
import com.machertecnologia.crudmarcher.usuario.model.RoleUsuario;
import com.machertecnologia.crudmarcher.usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class AutorizacaoService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CredencialUsuarioPort credencialUsuarioAdapter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credencialUsuarioAdapter.getByLogin(username);
    }

    public CredencialUsuario register(String login, String password, RoleUsuario role) throws Exception{
        if(this.credencialUsuarioAdapter.getByLogin(login) != null){
            throw new Exception("O usuário já existe");
        }

        String encryptedPasword = passwordEncoder.encode(password);
        var novaCredencialUsuario = new CredencialUsuario(login, encryptedPasword, role);
        credencialUsuarioAdapter.salvar(novaCredencialUsuario);
        return novaCredencialUsuario;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public CredencialUsuario update(Authentication authentication, AtualizacaoUsuarioDTO usuario, Usuario usuarioExistente) {
        CredencialUsuario credencialUsuario = null;
        if(isAdmin(authentication)) {
             credencialUsuario =
                    new CredencialUsuario(
                            Objects.requireNonNullElse(usuario.login(), usuarioExistente.getCredencialUsuario().getLogin()),
                            Objects.requireNonNullElse(passwordEncoder.encode(usuario.password()), usuarioExistente.getCredencialUsuario().getPassword()),
                            Objects.requireNonNullElse(usuario.role(), usuarioExistente.getCredencialUsuario().getRole())
                    );

        }
        return credencialUsuario;
    }

    public void delete(CredencialUsuario credencialUsuario) {
        credencialUsuarioAdapter.delete(credencialUsuario);
    }

    public Boolean isAdmin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }
}

