package com.dtplan.domain.usuario.dto;

public record TokenDTO(
        String token,
        String expires_in,
        long usuari_id,
        String usuario_nome
) {}
