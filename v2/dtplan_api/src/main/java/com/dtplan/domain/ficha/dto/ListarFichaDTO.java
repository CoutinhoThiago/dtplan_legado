package com.dtplan.domain.ficha.dto;

import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.fichaExercicio.dto.FichaExercicioDTO;

import java.util.List;
import java.util.stream.Collectors;

public record ListarFichaDTO(
        Long id,
        Long treinoId,
        String nome,
        List<FichaExercicioDTO> fichaExercicios
) {
    public ListarFichaDTO(Ficha ficha) {
        this(
                ficha.getId(),
                ficha.getTreino().getId(),
                ficha.getNome(),
                ficha.getFichaExercicios().stream()
                        .map(FichaExercicioDTO::new) // Mapeia cada FichaExercicio para um FichaExercicioDTO
                        .collect(Collectors.toList()) // Coleta os resultados em uma lista
        );
    }
}
