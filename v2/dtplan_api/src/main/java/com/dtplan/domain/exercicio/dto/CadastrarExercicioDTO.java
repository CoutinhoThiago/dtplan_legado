package com.dtplan.domain.exercicio.dto;

//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public record CadastrarExercicioDTO(
        //@NotBlank
        String nome,
        //@NotBlank
        boolean ativo,
        //@NotBlank
        int tipo,
        //@NotNull
        String observacoes,

        String musculo_alvo
) {
}
