package com.dtplan.domain.refeicao.dto;

import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;

import java.util.List;

public record CadastrarRefeicaoDTO(
        String descricao,
        Long dieta,

        List<RefeicaoAlimento> refeicaoAlimentos
) {}
