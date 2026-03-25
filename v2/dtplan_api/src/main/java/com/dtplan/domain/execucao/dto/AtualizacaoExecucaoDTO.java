package com.dtplan.domain.execucao.dto;

import java.util.List;

public record AtualizacaoExecucaoDTO(
        String observacao,
        List<Long> exerciciosIds
) {}