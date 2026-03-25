package com.dtplan.domain.ficha.dto;

import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.treino.Treino;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CadastrarFichaDTO(
        String nome,
        Long treino,
        List<Long> exercicios
) {
    public CadastrarFichaDTO {
        // Se exercicios for null, inicializa como uma lista vazia
        if (exercicios == null) {
            exercicios = Collections.emptyList();
        }
    }
}
