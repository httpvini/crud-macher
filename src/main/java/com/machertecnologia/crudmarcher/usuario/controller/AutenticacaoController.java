package com.machertecnologia.crudmarcher.usuario.controller;

import com.machertecnologia.crudmarcher.usuario.DTO.AutenticacaoDTO;
import com.machertecnologia.crudmarcher.usuario.DTO.LoginResponseDTO;
import com.machertecnologia.crudmarcher.usuario.DTO.RegistroDTO;
import com.machertecnologia.crudmarcher.usuario.model.CredencialUsuario;
import com.machertecnologia.crudmarcher.usuario.model.RoleUsuario;
import com.machertecnologia.crudmarcher.usuario.service.AutorizacaoService;
import com.machertecnologia.crudmarcher.usuario.service.TokenService;
import com.machertecnologia.crudmarcher.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutorizacaoService autorizacaoService;
    @Autowired
    private UsuarioService usuarioService;

    @Value("${api-key.valor}")
    private String apiKeyValida;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(autenticacaoDTO.login(), autenticacaoDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((CredencialUsuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegistroDTO registroDTO,
                                   @RequestHeader(name = "x-api-key") String apiKey) {
        if (!validarApiKey(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API key inválida");
        }

        String login = registroDTO.login();
        String password = registroDTO.password();
        RoleUsuario role = registroDTO.role();
        CredencialUsuario credencialUsuario;

        credencialUsuario = autorizacaoService.register(login, password, role);

        return ResponseEntity.ok().body(credencialUsuario.getLogin());
    }

    private boolean validarApiKey(String apiKey) {
        return apiKeyValida.equals(apiKey);
    }

}
