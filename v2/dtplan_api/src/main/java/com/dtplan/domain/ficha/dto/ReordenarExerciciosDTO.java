package com.dtplan.domain.ficha.dto;

import java.util.List;

public record ReordenarExerciciosDTO(
        Long fichaId,
        List<Long> novaOrdemIds // Lista de IDs dos exercícios na nova ordem
) {}
