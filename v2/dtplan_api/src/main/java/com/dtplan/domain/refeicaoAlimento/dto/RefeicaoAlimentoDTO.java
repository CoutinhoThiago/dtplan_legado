package com.dtplan.domain.refeicaoAlimento.dto;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.alimento.dto.DetalharAlimentoDTO;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;

public record RefeicaoAlimentoDTO(
		Long id,
		Long refeicaoId,
		Long alimentoId,
		double quantidade,
		DetalharAlimentoDTO alimento
) {

	// Construtor para inicializar o DTO a partir de RefeicaoAlimento
	public RefeicaoAlimentoDTO(RefeicaoAlimento refeicaoAlimento) {
		this(
				refeicaoAlimento.getId(),
				refeicaoAlimento.getRefeicao().getId(),
				refeicaoAlimento.getAlimento().getId(),
				refeicaoAlimento.getQuantidade(),
				new DetalharAlimentoDTO(refeicaoAlimento.getAlimento()) // Converte Alimento para DetalharAlimentoDTO
		);
	}
}