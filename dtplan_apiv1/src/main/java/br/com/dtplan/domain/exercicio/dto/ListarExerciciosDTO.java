package br.com.dtplan.domain.exercicio.dto;

import br.com.dtplan.domain.exercicio.Exercicio;

public record ListarExerciciosDTO(Long id, String descricao, boolean ativo, int tipo, String musculo_alvo, int series, int repeticoes_min, int repeticoes_max, double carga, int duracao_minutos, int intensidade) {
	public ListarExerciciosDTO(Exercicio exercicio) {
		this(
				exercicio.getId(), 
				exercicio.getDescricao(),
				exercicio.isAtivo(),
				exercicio.getTipo(),
				exercicio.getMusculo_alvo(),
				exercicio.getSeries(),
				exercicio.getRepeticoes_max(),
				exercicio.getRepeticoes_max(),
				exercicio.getCarga(),
				exercicio.getDuracao_minutos(),
				exercicio.getIntensidade()
				);
	}
}