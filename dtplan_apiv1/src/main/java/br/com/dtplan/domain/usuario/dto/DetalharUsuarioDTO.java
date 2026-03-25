package br.com.dtplan.domain.usuario.dto;

import br.com.dtplan.domain.usuario.Usuario;

public record DetalharUsuarioDTO(long id, String login, String nome, String cpf) {
    public DetalharUsuarioDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getLogin(),
                //usuario.getRole(),

                usuario.getNome(),
                usuario.getCpf()
        );
    }
}
