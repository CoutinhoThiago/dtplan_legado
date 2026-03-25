package com.dtplan.domain.ficha;

import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.fichaExercicio.FichaExercicio;
import com.dtplan.domain.treino.Treino;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "fichas")
@Entity(name = "Ficha")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ficha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    //private String descricao;
    //private String observacoes;

    @ManyToOne
    @JoinColumn(name = "treino_id")
    private Treino treino;

    @OneToMany(mappedBy = "ficha")
    private List<FichaExercicio> fichaExercicios;


    public Ficha(String nome, Treino  treino) {
        this.nome = nome;
        this.treino = treino;
    }
    public void atualizarInformacoes(String nome, List<Exercicio> exercicios) {
        if (nome != null) {
            this.nome = nome;
        }
    }

    public void adicionarExercicios(List<Exercicio> exercicios) {
        if (this.fichaExercicios == null) {
            this.fichaExercicios = new ArrayList<>();
        }

        int ordem = this.fichaExercicios.size();

        for (Exercicio exercicio : exercicios) {
            FichaExercicio fichaExercicio = new FichaExercicio();
            fichaExercicio.setExercicio(exercicio);
            fichaExercicio.setFicha(this);
            fichaExercicio.setOrdem(ordem++);
            this.fichaExercicios.add(fichaExercicio);
        }
    }
}