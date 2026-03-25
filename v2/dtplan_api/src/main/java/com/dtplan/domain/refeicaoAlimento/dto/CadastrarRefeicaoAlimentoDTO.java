package com.dtplan.domain.refeicaoAlimento.dto;

import com.dtplan.domain.alimento.dto.DetalharAlimentoDTO;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;

public record CadastrarRefeicaoAlimentoDTO(
		Long refeicaoId,
		Long alimentoId,
		double quantidade
) {

	// Construtor para inicializar o DTO a partir de RefeicaoAlimento
	public CadastrarRefeicaoAlimentoDTO(RefeicaoAlimento refeicaoAlimento) {
		this(
				refeicaoAlimento.getRefeicao().getId(),
				refeicaoAlimento.getAlimento().getId(),
				refeicaoAlimento.getQuantidade()
		);
	}
}