package com.dtplan.domain.dieta.dto;

import com.dtplan.domain.dieta.TipoDieta;
import com.dtplan.domain.usuario.Usuario;

public record CadastrarDietaDTO(
        //@NotBlank
        String descricao,
        //@NotBlank
        String autor,
        //@NotNull
        TipoDieta tipo,
        //@NotNull
        Usuario usuario
) {
}