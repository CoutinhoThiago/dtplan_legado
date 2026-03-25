package com.dtplan.domain.usuario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) // Ignora campos nulos no JSON
public record UsuarioDTO(
        @JsonProperty("id") Long id,
        @JsonProperty("nome") String nome,
        @JsonProperty("login") String login,
        @JsonProperty("documento") String documento
) {}