package com.dtplan.domain.usuario.dto;

import com.dtplan.domain.usuario.Usuario;

import java.util.Date;

public record DetalharUsuarioBasicoDTO(
        long id,
        String nome,
        String cpf,
        String dataNascimento,
        double altura,
        double pesoAtual,
        Enum nivelAtividade,
        Enum objetivo,
        String tipoUsuario,
        String cref,
        String crn
) {
    public DetalharUsuarioBasicoDTO(Usuario usuario, boolean completo) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                completo ? usuario.getDataNascimento() : null,
                completo ? usuario.getAltura() : 0.0,
                completo ? usuario.getPesoAtual() : 0.0,
                completo ? usuario.getNivelAtividade() : null,
                completo ? usuario.getObjetivo() : null,
                usuario.getTipoUsuario(),
                usuario.getCref(),
                usuario.getCrn()
        );
    }
}