package com.dtplan.domain.treino;

import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.treino.dto.CadastroTreinoDTO;
import com.dtplan.domain.treino.dto.EditarTreinoDTO;
import com.dtplan.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "treinos")
@Entity(name = "Treino")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;

    //@Enumerated(EnumType.STRING)
    //private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "treino")
    private List<Ficha> fichas;

    public Treino(String nome, String descricao, Usuario autor, Usuario usuario) {
        this.nome = nome;
        this.descricao = descricao;
        this.autor = autor;
        //this.tipo = dados.tipo();
        this.usuario = usuario;
    }

    public void atualizarInformacoes(EditarTreinoDTO dados) {
        if (nome != null) {
            this.nome = dados.nome();
        }
        if (descricao != null) {
            this.descricao = dados.descricao();
        }
        //if (autor != null) {
        //    this.autor = dados.autor();
        //}
        //if (usuario != null) {
        //    this.usuario = dados.usuario();
        //}
        //if (tipo != null) {
        //    this.tipo = Tipo.valueOf(dados.tipo());
        //}
    }
}