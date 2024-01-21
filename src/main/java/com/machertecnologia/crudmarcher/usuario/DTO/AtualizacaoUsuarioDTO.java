package com.machertecnologia.crudmarcher.usuario.DTO;

import com.machertecnologia.crudmarcher.usuario.model.Endereco;
import com.machertecnologia.crudmarcher.usuario.model.RoleUsuario;

import java.time.LocalDate;

public record AtualizacaoUsuarioDTO(String cpf, String nome, LocalDate nascimento, Endereco endereco, String login, String password, RoleUsuario role) {
}
