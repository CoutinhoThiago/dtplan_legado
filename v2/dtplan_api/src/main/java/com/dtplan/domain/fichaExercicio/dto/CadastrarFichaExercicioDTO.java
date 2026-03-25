package com.dtplan.domain.fichaExercicio.dto;

public record CadastrarFichaExercicioDTO(
    Long fichaId,
    Long exercicioId,

    int repeticoes,
    int series,
    double carga,
    //Integer repeticoes_min,
    //Integer repeticoes_max,

    int duracao_minutos,
    int intensidade
) {
}
