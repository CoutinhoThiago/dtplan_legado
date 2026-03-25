package br.com.dtplan.domain.usuario;

public enum PermissaoDeUsuario {
    ADMIN("admin"),
    USER("user");

    private String permissao;

    PermissaoDeUsuario(String permissao) {
        this.permissao = permissao;
    }

    public String etRole() {return permissao;}
}