package com.dtplan.domain.execucao.dto;

import com.dtplan.domain.execucao.Execucao;
import com.dtplan.domain.execucaoExercicio.ExecucaoExercicio;
import com.dtplan.domain.execucaoExercicio.dto.DetalharExecucaoExercicioDTO;
import com.dtplan.domain.execucaoExercicio.dto.ExecucaoExercicioDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record DetalharExecucaoDTO(
        Long id,
        LocalDate data,
        String observacao,
        Long treinoId,
        Long fichaId,
        List<ExecucaoExercicioDTO> execucaoExercicios
) {
    public DetalharExecucaoDTO(Execucao execucao, List<ExecucaoExercicio> execucaoExercicios) {
        this(
                execucao.getId(),
                execucao.getData(),
                execucao.getObservacao(),
                execucao.getTreino().getId(),
                execucao.getFicha().getId(),
                //execucao.getExecucaoExercicios().stream()
                //        .map(ExecucaoExercicioDTO::new)
                //        .collect(Collectors.toList())
                execucaoExercicios.stream()
                        .map(ExecucaoExercicioDTO::new)
                        .collect(Collectors.toList())
        );
    }
}
