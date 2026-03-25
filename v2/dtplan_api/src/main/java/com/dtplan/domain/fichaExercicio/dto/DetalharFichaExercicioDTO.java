package com.dtplan.domain.fichaExercicio.dto;

import com.dtplan.domain.fichaExercicio.FichaExercicio;

public record DetalharFichaExercicioDTO(
		Long id,

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

	// Construtor para inicializar o DTO a partir de FichaExercicio
	public DetalharFichaExercicioDTO(FichaExercicio fichaExercicio) {
		this(
				fichaExercicio.getId(),
				fichaExercicio.getFicha().getId(),
				fichaExercicio.getExercicio().getId(),

				fichaExercicio.getRepeticoes(),
				fichaExercicio.getSeries(),
				fichaExercicio.getCarga(),

				fichaExercicio.getDuracao_minutos(),
				fichaExercicio.getIntensidade()
		);
	}
}