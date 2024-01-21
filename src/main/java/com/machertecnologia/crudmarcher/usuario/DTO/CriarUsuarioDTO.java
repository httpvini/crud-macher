package com.machertecnologia.crudmarcher.usuario.DTO;

import com.machertecnologia.crudmarcher.usuario.model.Endereco;
import com.machertecnologia.crudmarcher.usuario.model.RoleUsuario;

import java.time.LocalDate;

public record CriarUsuarioDTO(String cpf, String nome, LocalDate dataNascimento, Endereco endereco, String login, String password, RoleUsuario role) {
}
