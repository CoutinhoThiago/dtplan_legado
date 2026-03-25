package com.dtplan.domain.dieta;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.dieta.dto.CadastrarDietaDTO;
import com.dtplan.domain.dieta.dto.EditarDietaDTO;
import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicao.RefeicaoRepository;
import com.dtplan.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "dietas")
@Entity(name = "Dieta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Dieta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String autor;

    @Enumerated(EnumType.STRING)
    private TipoDieta tipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // , nullable = false)
    private Usuario usuario;

    private Float calorias; //kcal //Energia:
    private Float proteina;//g
    private Float gordura; //lipideos //g
    private Float carboidrato; //g
    private Float fibraAlimentar; //g

    @OneToMany(mappedBy = "dieta")
    private List<Refeicao> refeicoes;

    public Dieta(CadastrarDietaDTO dados) {
        this.descricao = dados.descricao();
        this.autor = dados.autor();
        this.tipo = dados.tipo();
        this.usuario = dados.usuario();
    }

    public void atualizaInformacoes(EditarDietaDTO dados) {
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if (dados.autor() != null) {
            this.autor = dados.autor();
        }
        if (dados.tipo() != null) {
            this.tipo = dados.tipo();
        }
        if (dados.usuario() != null) {
            this.usuario = dados.usuario();
        }
    }

    public void atualizarNutrientesDieta(Dieta dieta) {
        float totalCalorias = 0;
        float totalProteina = 0;
        float totalGordura = 0;
        float totalCarboidrato = 0;
        float totalFibraAlimentar = 0;

        if (dieta.getRefeicoes() != null) {
            for (Refeicao refeicao : dieta.getRefeicoes()) {
                totalCalorias += refeicao.getCalorias();
                totalProteina += refeicao.getProteina();
                totalGordura += refeicao.getGordura();
                totalCarboidrato += refeicao.getCarboidrato();
                totalFibraAlimentar += refeicao.getFibraAlimentar();
            }
        }

        dieta.calorias = totalCalorias;
        dieta.proteina = totalProteina;
        dieta.gordura = totalGordura;
        dieta.carboidrato = totalCarboidrato;
        dieta.fibraAlimentar = totalFibraAlimentar;
    }

    @Transactional
    public void adicionarRefeicaoNaDieta(Dieta dieta, Refeicao refeicao) {
        dieta.getRefeicoes().add(refeicao);
        atualizarNutrientesDieta(dieta);
    }

    @Transactional
    public void removerRefeicaoDaDieta(Dieta dieta, Refeicao refeicao) {
        if (dieta.getRefeicoes() != null) {
            dieta.getRefeicoes().remove(refeicao);
            atualizarNutrientesDieta(dieta);
        }
    }


public void adicionarRefeicoesPorIds(List<Long> idsRefeicoes, RefeicaoRepository refeicaoRepository) {
        List<Refeicao> refeicoesNovas = refeicaoRepository.findByIdIn(idsRefeicoes);
        if (this.refeicoes == null) {
            this.refeicoes = refeicoesNovas; // Inicializa a lista se estiver vazia
        } else {
            this.refeicoes.addAll(refeicoesNovas); // Adiciona as novas refeições à lista existente
        }
    }
}
