package com.dtplan.domain.refeicaoAlimento.dto;

import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;

public record DetalharRefeicaoAlimentoDTO(
		Long id,

		Long refeicaoId,
		Long alimentoId,

		double quantidade
) {

	// Construtor para inicializar o DTO a partir de FichaExercicio
	public DetalharRefeicaoAlimentoDTO(RefeicaoAlimento refeicaoAlimento) {
		this(
				refeicaoAlimento.getId(),
				refeicaoAlimento.getRefeicao().getId(),
				refeicaoAlimento.getAlimento().getId(),
				refeicaoAlimento.getQuantidade()
		);
	}
}