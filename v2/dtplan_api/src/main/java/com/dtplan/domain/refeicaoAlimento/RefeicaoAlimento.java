package com.dtplan.domain.refeicaoAlimento;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.refeicao.Refeicao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "refeicao_alimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RefeicaoAlimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "refeicao_id", nullable = false)
    private Refeicao refeicao;

    @ManyToOne
    @JoinColumn(name = "alimento_id", nullable = false)
    private Alimento alimento;

    private double quantidade; // Quantidade do alimento na refeição (em gramas, por exemplo)

    public RefeicaoAlimento(Refeicao refeicao, Alimento alimento, double quantidade) {
        this.refeicao = refeicao;
        this.alimento = alimento;
        this.quantidade = quantidade;
    }
}