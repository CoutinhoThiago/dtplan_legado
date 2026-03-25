package com.dtplan.domain.execucao;

import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.execucaoExercicio.ExecucaoExercicio;
import com.dtplan.domain.treino.Treino;
import jakarta.persistence.*;
import lombok.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "execucao")
@Entity(name = "Execucao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Execucao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private String nome;
    //private String descricao;
    //private String observacoes;

    private LocalDate data;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "treino_id")
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;

    @OneToMany(mappedBy = "execucao")
    private List<ExecucaoExercicio> execucaoExercicios;


    public Execucao(LocalDate data, String observacao, Treino  treino, Ficha ficha) {
        this.data = data;
        this.observacao = observacao;
        this.treino = treino;
        this.ficha = ficha;
    }
    public void atualizarInformacoes(String observacao, List<Exercicio> exercicios) {
        if (observacao != null) {
            this.observacao = observacao;
        }
    }

    public void adicionarExercicios(List<Exercicio> exercicios) {
        if (this.execucaoExercicios == null) {
            this.execucaoExercicios = new ArrayList<>();
        }

        int ordem = this.execucaoExercicios.size();

        for (Exercicio exercicio : exercicios) {
            ExecucaoExercicio execucaoExercicio = new ExecucaoExercicio();
            execucaoExercicio.setExercicio(exercicio);
            execucaoExercicio.setFicha(this.ficha);
            execucaoExercicio.setOrdem(ordem++);
            execucaoExercicio.setExecucao(this);
            this.execucaoExercicios.add(execucaoExercicio);
        }
    }

    // CORREÇÃO: Método renomeado e lógica ajustada
    public void adicionarExecucoesExercicios(List<ExecucaoExercicio> novasExecucoes) {
        if (this.execucaoExercicios == null) {
            this.execucaoExercicios = new ArrayList<>();
        }

        for (ExecucaoExercicio execucaoExercicio : novasExecucoes) {
            execucaoExercicio.setExecucao(this);
            this.execucaoExercicios.add(execucaoExercicio);
        }
    }
}