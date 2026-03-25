package com.dtplan.domain.execucao.dto;

import com.dtplan.domain.execucao.Execucao;
import com.dtplan.domain.execucaoExercicio.dto.ExecucaoExercicioDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ListarExecucaoDTO(
        Long id,
        //LocalDateTime dataInicio,
        //LocalDateTime dataFim,
        //Integer tempoTotal,
        //String observacoes,
        Long fichaId,
        String nomeFicha,
        List<ExecucaoExercicioDTO> execucaoExercicios
) {
    public ListarExecucaoDTO(Execucao execucao) {
        this(
                execucao.getId(),
                //execucao.getDataInicio(),
                //execucao.getDataFim(),
                //execucao.getTempoTotal(),
                //execucao.getObservacoes(),
                execucao.getFicha() != null ? execucao.getFicha().getId() : null,
                execucao.getFicha() != null ? execucao.getFicha().getNome() : "Sem ficha",
                execucao.getExecucaoExercicios() != null ?
                        execucao.getExecucaoExercicios().stream()
                                .map(ExecucaoExercicioDTO::new)
                                .collect(Collectors.toList()) :
                        List.of()
        );
    }
}