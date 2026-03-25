package com.dtplan.domain.alimento.dto;

import com.dtplan.domain.alimento.Alimento;

public record DetalharAlimentoDTO(
        long id,
        String nome,
        Float calorias,
        Float proteina,
        Float gordura,
        Float carboidrato,
        Float fibraAlimentar,

        Long colesterol,
        Long ferro,
        Long calcio,
        Long sodio,
        Long magnesio,
        Long potassio,
        Long manganes,
        Long fosforo,
        Long cobre,
        Long zinco,
        Long niacina,

        Long vitaminaC
) {

    public DetalharAlimentoDTO(Alimento dados) {
        this(
                dados.getId(),
                dados.getNome(),
                dados.getCalorias(),
                dados.getProteina(),
                dados.getGordura(),
                dados.getCarboidrato(),
                dados.getFibraAlimentar(),
                dados.getColesterol(),
                dados.getFerro(),
                dados.getCalcio(),
                dados.getSodio(),
                dados.getMagnesio(),
                dados.getPotassio(),
                dados.getManganes(),
                dados.getFosforo(),
                dados.getCobre(),
                dados.getZinco(),
                dados.getNiacina(),
                dados.getVitaminaC()
        );
    }
}
