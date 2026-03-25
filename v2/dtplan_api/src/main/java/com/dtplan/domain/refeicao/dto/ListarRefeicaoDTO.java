package com.dtplan.domain.refeicao.dto;

import com.dtplan.domain.alimento.dto.DetalharAlimentoDTO;
import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;
import com.dtplan.domain.refeicaoAlimento.dto.RefeicaoAlimentoDTO;

import java.util.Collections;
import java.util.List;

public record ListarRefeicaoDTO(
        Long id,
        String descricao,
        Long dietaId,
        List<RefeicaoAlimentoDTO> refeicaoAlimentos
) {
    public ListarRefeicaoDTO(Refeicao refeicao, List<RefeicaoAlimentoDTO> refeicaoAlimentos) {
        this(
                refeicao.getId(),
                refeicao.getDescricao(),
                refeicao.getDieta().getId(),
                refeicaoAlimentos
        );
    }
}