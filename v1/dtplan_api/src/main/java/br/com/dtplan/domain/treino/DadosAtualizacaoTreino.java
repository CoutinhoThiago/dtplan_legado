package br.com.dtplan.domain.treino;

import java.util.List;

import br.com.dtplan.domain.exercicio.Exercicio;

public record DadosAtualizacaoTreino(
		String descricao,
		String autor,
		String tipo
		) {
}
