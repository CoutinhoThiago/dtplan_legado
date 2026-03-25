package com.dtplan.domain.usuario.dto;

import com.dtplan.domain.usuario.Genero;
import com.dtplan.domain.usuario.Usuario;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record DetalharUsuarioDTO(
        long id,
        String email,
        String senha,
        String nome,
        String cpf,
        Genero genero,
        String dataNascimento,
        double altura,
        double pesoAtual,
        Enum nivelAtividade,
        Enum objetivo,
        String tipoUsuario,
        String cref,
        List<DetalharUsuarioBasicoDTO> alunos, // Lista de alunos (básicos ou completos)
        String crn,
        List<DetalharUsuarioBasicoDTO> pacientes // Lista de pacientes (básicos ou completos)
) {
    public DetalharUsuarioDTO(Usuario usuario, boolean alunosCompletos, boolean pacientesCompletos) {
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getGenero(),
                usuario.getDataNascimento(),
                usuario.getAltura(),
                usuario.getPesoAtual(),
                usuario.getNivelAtividade(),
                usuario.getObjetivo(),
                usuario.getTipoUsuario(),
                usuario.getCref(),
                // Inclui alunos com base no tipo de usuário e no parâmetro alunosCompletos
                usuario.getTipoUsuario().equals("ambos") || usuario.getTipoUsuario().equals("treinador") ?
                        usuario.getAlunos().stream()
                                .map(aluno -> new DetalharUsuarioBasicoDTO(aluno, alunosCompletos))
                                .collect(Collectors.toList()) :
                        null,
                usuario.getCrn(),
                // Inclui pacientes com base no tipo de usuário e no parâmetro pacientesCompletos
                usuario.getTipoUsuario().equals("ambos") || usuario.getTipoUsuario().equals("nutricionista") ?
                        usuario.getPacientes().stream()
                                .map(paciente -> new DetalharUsuarioBasicoDTO(paciente, pacientesCompletos))
                                .collect(Collectors.toList()) :
                        null
        );
    }
}