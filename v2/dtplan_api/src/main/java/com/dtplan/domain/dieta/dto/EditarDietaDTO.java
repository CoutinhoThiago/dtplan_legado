package com.dtplan.domain.dieta.dto;

import com.dtplan.domain.dieta.TipoDieta;
import com.dtplan.domain.usuario.Usuario;

public record EditarDietaDTO(
        String descricao,
        String autor,
        TipoDieta tipo,
        Usuario usuario,

        Float calorias,
        Float proteina,
        Float gordura,
        Float carboidrato,
        Float fibraAlimentar
) {

}