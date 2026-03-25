package com.dtplan.domain.execucao.dto;

public record ExercicioDTO(
        Long id,  // ID do exercício
        int repeticoes,
        int carga
) {}
