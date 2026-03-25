package com.dtplan.domain.fichaExercicio.dto;

public record EditarFichaExercicioDTO(
		Long fichaId,
		Long exercicioId,
		Integer ordem,
		Integer repeticoes,
		Integer series,
		Double carga,
		Integer duracao_minutos,
		Integer intensidade
) {}
