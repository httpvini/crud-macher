package com.machertecnologia.crudmarcher.usuario.exception;

public class UsuarioJaExisteException extends RuntimeException{

    private String username;

    public UsuarioJaExisteException() {
        super("Usuário já existe");
    }

    public UsuarioJaExisteException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
