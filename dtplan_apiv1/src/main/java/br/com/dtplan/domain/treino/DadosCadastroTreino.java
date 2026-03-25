package br.com.dtplan.domain.treino;

import java.util.List;

import br.com.dtplan.domain.exercicio.Exercicio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTreino(
		@NotBlank
		String descricao, 
		@NotBlank
		String autor, 
		@NotNull
		String tipo,
		@NotNull
		List<Long> exercicios_id
	) {
}
