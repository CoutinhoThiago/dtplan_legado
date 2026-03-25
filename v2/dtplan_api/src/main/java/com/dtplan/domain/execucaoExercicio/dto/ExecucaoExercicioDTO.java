package com.dtplan.domain.execucaoExercicio.dto;

import com.dtplan.domain.execucaoExercicio.ExecucaoExercicio;

public record ExecucaoExercicioDTO(
        Long id,
        Integer seriesRealizadas,
        Integer repeticoesRealizadas,
        Double peso,
        String observacao,
        Long exercicioId,
        String exercicioNome
) {
    public ExecucaoExercicioDTO(ExecucaoExercicio execucaoExercicio) {
        this(
                execucaoExercicio.getId(),
                execucaoExercicio.getSeriesRealizadas(),
                execucaoExercicio.getRepeticoesRealizadas(),
                execucaoExercicio.getPeso(),
                execucaoExercicio.getObservacao(),
                execucaoExercicio.getExercicio() != null ? execucaoExercicio.getExercicio().getId() : null,
                execucaoExercicio.getExercicio() != null ? execucaoExercicio.getExercicio().getNome() : "Exercício não especificado"
        );
    }
}