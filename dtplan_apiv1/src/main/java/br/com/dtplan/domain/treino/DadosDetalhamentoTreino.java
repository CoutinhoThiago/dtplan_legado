package br.com.dtplan.domain.treino;

import br.com.dtplan.domain.exercicio.Exercicio;

import java.util.List;
import java.util.stream.Collectors;

public record DadosDetalhamentoTreino(
		long id,
		String descricao,
		String autor,
		String tipo,
		List<String> exercicios
) {

	public DadosDetalhamentoTreino(Treino treino, List<Exercicio> exercicios) {
		this(
				treino.getId(),
				treino.getDescricao(),
				treino.getAutor(),
				treino.getTipo(),
				exercicios.stream().map(Exercicio::getDescricao).collect(Collectors.toList())
		);
	}

	public long getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public String getAutor() {
		return this.autor;
	}

	public String getTipo() {
		return this.tipo;
	}

	public List<String> getExercicios() {
		return this.exercicios;
	}

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof DadosDetalhamentoTreino)) return false;
		final DadosDetalhamentoTreino other = (DadosDetalhamentoTreino) o;
		if (!other.canEqual((Object) this)) return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof DadosDetalhamentoTreino;
	}

	public int hashCode() {
		int result = 1;
		return result;
	}

	public String toString() {
		return "DadosDetalhamentoTreino()";
	}
}
