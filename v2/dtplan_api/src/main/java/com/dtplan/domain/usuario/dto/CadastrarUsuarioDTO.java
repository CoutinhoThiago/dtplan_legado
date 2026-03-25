package com.dtplan.domain.usuario.dto;


import com.dtplan.domain.usuario.Genero;
import com.dtplan.domain.usuario.Permissao;

import java.util.Date;

public record CadastrarUsuarioDTO(
        String email,
        String senha,
        Permissao permissao,
        Genero genero,
        String nome,
        String cpf,
        String dataNascimento,
        int altura,
        int pesoAtual,
        String tipoUsuario,
        String cref,
        String crn

//        "dataNascimento": null,
//	      "altura": 0.0,
//        "pesoAtual": 0.0,
//        "nivelAtividade": null,
//        "objetivo": null
) {}
