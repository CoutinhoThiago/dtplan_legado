package com.dtplan.domain.refeicao;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.dieta.Dieta;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "refeicoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Refeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;

    private double calorias; // kcal // Energia:
    private double proteina; // g
    private double gordura; // lipídeos // g
    private double carboidrato; // g
    private double fibraAlimentar; // g

    @ManyToOne
    @JoinColumn(name = "dieta_id")
    private Dieta dieta;

    @OneToMany(mappedBy = "refeicao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefeicaoAlimento> refeicaoAlimentos = new ArrayList<>();

    public Refeicao(String descricao, Dieta dieta) {
        this.descricao = descricao;
        this.dieta = dieta;
    }

    public void atualizaInformacoes(String descricao, List<RefeicaoAlimento> refeicaoAlimentos) {
        if (descricao != null) {
            this.descricao = descricao;
        }
        if(refeicaoAlimentos != null) {
            this.refeicaoAlimentos = refeicaoAlimentos;
        }
    }

    // Método para adicionar um alimento com quantidade à refeição
    public void adicionarAlimento(Refeicao refeicao, Alimento alimento, double quantidade) {
        Optional<RefeicaoAlimento> existente = refeicao.getRefeicaoAlimentos().stream()
                .filter(ra -> ra.getAlimento().getId().equals(alimento.getId()))
                .findFirst();

        if (existente.isPresent()) {
            existente.get().setQuantidade(quantidade);
        } else {
            refeicao.getRefeicaoAlimentos().add(new RefeicaoAlimento(refeicao, alimento, quantidade));
        }
        atualizarNutrientesRefeicao();
    }

    public void editarAlimento(Refeicao refeicao, Long id, double novaQuantidade) {
        // Localiza o RefeicaoAlimento pelo ID do alimento e atualiza a quantidade
        refeicao.getRefeicaoAlimentos().stream()
                .filter(ra -> ra.getAlimento().getId().equals(id))
                .findFirst()
                .ifPresent(ra -> ra.setQuantidade(novaQuantidade));
        atualizarNutrientesRefeicao();
    }

    public void removerAlimento(Refeicao refeicao, Long id) {
        refeicao.getRefeicaoAlimentos().removeIf(refeicaoAlimento -> refeicaoAlimento.getId().equals(id));
        atualizarNutrientesRefeicao();
    }

    public void atualizarNutrientesRefeicao() {
        calorias = 0f;
        proteina = 0f;
        gordura = 0f;
        carboidrato = 0f;
        fibraAlimentar = 0f;

        for (RefeicaoAlimento refeicaoAlimento : refeicaoAlimentos) {
            Alimento alimento = refeicaoAlimento.getAlimento();
            double quantidade = refeicaoAlimento.getQuantidade();
            calorias += alimento.getCalorias() * quantidade;
            proteina += alimento.getProteina() * quantidade;
            gordura += alimento.getGordura() * quantidade;
            carboidrato += alimento.getCarboidrato() * quantidade;
            fibraAlimentar += alimento.getFibraAlimentar() * quantidade;
        }
    }
}
