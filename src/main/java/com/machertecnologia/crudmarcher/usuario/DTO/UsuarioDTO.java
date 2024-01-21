package com.machertecnologia.crudmarcher.usuario.DTO;

import com.machertecnologia.crudmarcher.usuario.model.Endereco;
import com.machertecnologia.crudmarcher.usuario.model.RoleUsuario;
import com.machertecnologia.crudmarcher.usuario.model.StatusRegistro;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioDTO(Long id,
                         String cpf,
                         String nome,
                         LocalDate dataNascimento,
                         Endereco endereco,
                         StatusRegistro status,
                         LocalDateTime dataCriacao,
                         String usuarioCriacao,
                         LocalDateTime dataAtualizacao,
                         String usuarioAtualizacao,
                         LocalDateTime dataRemocao,
                         String usuarioRemocao,
                         String login,
                         RoleUsuario role) {
}
