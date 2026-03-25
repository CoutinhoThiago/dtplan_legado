package com.dtplan.domain.usuario;

public enum Genero {
    MASCULINO("masculino"),
    FEMININO("feminino"),
    OUTRO("outro");

    private String genero;

    Genero(String permissao) {
        this.genero = genero;
    }

    public String etRole() {return genero;}
}