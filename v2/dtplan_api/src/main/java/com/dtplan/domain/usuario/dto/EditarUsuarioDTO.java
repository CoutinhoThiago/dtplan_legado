package com.dtplan.domain.usuario.dto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public record EditarUsuarioDTO(
        String email,
        Optional<String> senha,
        Optional<String> nome,
        Optional<String> cpf,
        Optional<String> dataNascimento,
        Optional<Integer> altura,
        Optional<Integer> pesoAtual,
        Optional<String> nivelAtividade,
        Optional<String> objetivo,
        Optional<String> tipoUsuario,
        Optional<String> cref,
        Optional<List<Long>> alunos,
        Optional<String> crn,
        Optional<List<Long>> pacients
) {}
