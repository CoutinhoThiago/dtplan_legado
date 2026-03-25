package com.dtplan.domain.dieta.dto;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.dieta.Dieta;
import com.dtplan.domain.dieta.TipoDieta;
import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicao.dto.ListarRefeicaoDTO;
import com.dtplan.domain.usuario.Usuario;

import java.util.List;

public record ListarDietaDTO(
long id,
String descricao,
String autor,
TipoDieta tipo,

String usuario,
List<ListarRefeicaoDTO> refeicoes
		) {

public ListarDietaDTO(Dieta dieta, List<ListarRefeicaoDTO> refeicoes) {
    this(
            dieta.getId(),
            dieta.getDescricao(),
            dieta.getAutor(),
            dieta.getTipo(),

            dieta.getUsuario().getNome(),
            refeicoes
    );
}}