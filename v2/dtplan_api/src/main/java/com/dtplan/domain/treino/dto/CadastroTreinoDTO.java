package com.dtplan.domain.treino.dto;

import com.dtplan.domain.usuario.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) // Ignora campos nulos no JSON
public record CadastroTreinoDTO(
		String nome,
		String descricao,
		UsuarioDTO autor,
		UsuarioDTO usuario // Objeto usuario flexível
) {
}