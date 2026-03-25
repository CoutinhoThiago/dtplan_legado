package com.dtplan.domain.usuario;

public enum Permissao {
    ADMIN("admin"),
    USER("user");

    private String permissao;

    Permissao(String permissao) {
        this.permissao = permissao;
    }

    public String etRole() {return permissao;}
}
