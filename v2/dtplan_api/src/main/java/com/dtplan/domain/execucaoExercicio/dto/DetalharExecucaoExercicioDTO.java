package com.dtplan.domain.execucaoExercicio.dto;

import com.dtplan.domain.execucaoExercicio.ExecucaoExercicio;

public record DetalharExecucaoExercicioDTO(
        Long id
) {
    public DetalharExecucaoExercicioDTO(ExecucaoExercicio execucaoExercicio) {
        this(
            execucaoExercicio.getId()
        );
    }
}
