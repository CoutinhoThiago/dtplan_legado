package com.dtplan.domain.treino.dto;

import com.dtplan.domain.usuario.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) // Ignora campos nulos no JSON
public record EditarTreinoDTO(
		String nome,
		String descricao,
		//String tipo
		UsuarioDTO autor,
		UsuarioDTO usuario

		) {
}
