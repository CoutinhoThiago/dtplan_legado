package br.com.dtplan.domain.exercicio.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastrarExercicioDTO(
		@NotBlank
		String descricao,
		@NotBlank
		boolean ativo,
		@NotBlank
	    int tipo,

	    String musculo_alvo,
	    int series,
	    int repeticoes_min,
	    int repeticoes_max,
	    double carga,
	    
	    int duracao_minutos,
	    int intensidade
	) {
}
