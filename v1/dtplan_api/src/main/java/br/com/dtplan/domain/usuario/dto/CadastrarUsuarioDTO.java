package br.com.dtplan.domain.usuario.dto;

public record CadastrarUsuarioDTO(
        String login,
        String senha,
        //UserRole role,
        String nome,
        String cpf
) {}
