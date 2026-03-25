package com.dtplan.domain.ficha.dto;

import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.fichaExercicio.dto.FichaExercicioDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record DetalharFichaDTO(
		Long id,
		Long treinoId,
		String nome,
		List<FichaExercicioDTO> fichaExercicios
) {
	public DetalharFichaDTO(Ficha ficha) {
		this(
				ficha.getId(),
				ficha.getTreino().getId(),
				ficha.getNome(),
				ficha.getFichaExercicios() != null ?
						ficha.getFichaExercicios().stream()
								.map(FichaExercicioDTO::new)
								.collect(Collectors.toList())
						: new ArrayList<>() // Retorna uma lista vazia se fichaExercicios for null
		);
	}
}