package com.dtplan.domain.refeicao.dto;

import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;

import java.util.List;

public record EditarRefeicaoDTO(
		String descricao,
		List<RefeicaoAlimento> refeicaoAlimentos
		) {
}
