package com.dtplan.domain.exercicio.dto;

//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public record EditarExercicioDTO(
		//@NotNull
	    long id,
		//@NotBlank
		String nome,
		//@NotBlank
		boolean ativo,
		//@NotBlank
	    int tipo,
		//@NotNull
		String observacao,
	    
	    String musculo_alvo
	) {


}


