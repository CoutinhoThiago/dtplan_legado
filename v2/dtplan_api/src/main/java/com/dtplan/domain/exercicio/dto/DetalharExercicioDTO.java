package com.dtplan.domain.exercicio.dto;

import com.dtplan.domain.exercicio.Exercicio;

public record DetalharExercicioDTO(
		Long id,
		String nome,
		boolean ativo,
		Integer tipo,
		String observacoes,
		String musculo_alvo
) {
	public DetalharExercicioDTO(Exercicio exercicio) {
		this(
				exercicio.getId(), 
				exercicio.getNome(),
				exercicio.isAtivo(),
				exercicio.getTipo(),
				exercicio.getObservacoes(),

				exercicio.getMusculo_alvo()
				);
	}
}
