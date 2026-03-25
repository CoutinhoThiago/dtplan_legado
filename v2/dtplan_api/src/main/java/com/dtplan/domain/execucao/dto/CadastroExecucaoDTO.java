package com.dtplan.domain.execucao.dto;

import com.dtplan.domain.execucaoExercicio.dto.ExecucaoExercicioDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record CadastroExecucaoDTO(
        Long treinoId,
        Long fichaId,

        LocalDate data,
        String observacao,

        List<ExecucaoExercicioDTO> execucaoExercicios
) {}