package com.dtplan.domain.alimento.dto;

import com.dtplan.domain.alimento.Alimento;

public record ListarAlimentosDTO (
    Long id,
    String nome
) {
    public ListarAlimentosDTO(Alimento alimento) {
        this(
                alimento.getId(),
                alimento.getNome()
                );
    }
}
