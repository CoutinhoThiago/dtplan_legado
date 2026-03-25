package com.dtplan.domain.ficha.dto;

import com.dtplan.domain.fichaExercicio.dto.EditarFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.FichaExercicioDTO;

import java.util.List;

public record EditarFichaDTO(
        String nome,
        List<EditarFichaExercicioDTO> fichaExercicios
) {}

