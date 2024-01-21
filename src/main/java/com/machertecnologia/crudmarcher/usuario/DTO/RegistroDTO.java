package com.machertecnologia.crudmarcher.usuario.DTO;

import com.machertecnologia.crudmarcher.usuario.model.RoleUsuario;

public record RegistroDTO(String login, String password, RoleUsuario role) {
}
