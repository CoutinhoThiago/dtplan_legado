package com.dtplan.domain.alimento.dto;

public record CadastrarAlimentoDTO (
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
) {}
