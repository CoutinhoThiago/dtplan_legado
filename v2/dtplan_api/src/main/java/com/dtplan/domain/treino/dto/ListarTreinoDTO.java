package com.dtplan.domain.treino.dto;

import com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import com.dtplan.domain.treino.Tipo;
import com.dtplan.domain.treino.Treino;
import com.dtplan.domain.usuario.Usuario;
import com.dtplan.domain.usuario.dto.DetalharUsuarioBasicoDTO;
import com.dtplan.domain.usuario.dto.DetalharUsuarioDTO;

public record ListarTreinoDTO(
		Long id,
		String nome,
		String descricao,
		DetalharUsuarioDTO autor,
		DetalharUsuarioDTO usuario
		//Tipo tipo
) {
	public ListarTreinoDTO(Treino treino) {
		this(
				treino.getId(),
				treino.getNome(),
				treino.getDescricao(),

				new DetalharUsuarioDTO(treino.getAutor(), false, false),
				new DetalharUsuarioDTO(treino.getUsuario(), false, false)

				//treino.getTipo()
		);
	}
}