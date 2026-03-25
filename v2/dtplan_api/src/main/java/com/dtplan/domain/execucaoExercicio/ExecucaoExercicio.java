package com.dtplan.domain.execucaoExercicio;

import com.dtplan.domain.execucao.Execucao;
import com.dtplan.domain.execucaoExercicio.dto.CadastrarExecucaoExercicioDTO;
import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import com.dtplan.domain.ficha.Ficha;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "execucao_exercicio")
@Entity(name = "ExecucaoExercicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ExecucaoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ordem;
    private Integer seriesRealizadas;
    private Integer repeticoesRealizadas;
    private Double peso;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "execucao_id")
    private Execucao execucao;

    @ManyToOne
    @JoinColumn(name = "exercicio_id")
    private Exercicio exercicio;

    @ManyToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;

    public ExecucaoExercicio(CadastrarExecucaoExercicioDTO dados) {
        this.ordem = null; //dados.ordem()
        this.seriesRealizadas = null;
        this.repeticoesRealizadas = null;
        this.peso = null;
        this.observacao = null;
        this.execucao = null;
        this.exercicio = null;
        this.ficha = null;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
}
