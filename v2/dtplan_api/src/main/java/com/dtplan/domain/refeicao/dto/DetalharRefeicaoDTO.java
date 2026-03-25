package com.dtplan.domain.refeicao.dto;

import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;
import com.dtplan.domain.refeicaoAlimento.dto.RefeicaoAlimentoDTO;

import java.util.List;

public record DetalharRefeicaoDTO(
		long id,
		String descricao,
		Long dieta,

		List<RefeicaoAlimentoDTO> refeicaoAlimentos
) {

	public DetalharRefeicaoDTO(Refeicao refeicao, List<RefeicaoAlimentoDTO> refeicaoAlimentos) {
		this(
				refeicao.getId(),
				refeicao.getDescricao(),
				refeicao.getDieta().getId(),

				refeicaoAlimentos
        );
	}
}
