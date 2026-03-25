package com.dtplan.domain.alimento;

import com.dtplan.domain.alimento.dto.EditarAlimentoDTO;
import com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "alimento")
@Entity(name = "Alimento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;


    private Float calorias; //kcal //Energia:
    private Float proteina;//g
    private Float gordura; //lipideos //g
    private Float carboidrato; //g
    @Column(name = "fibraAlimentar")
    private Float fibraAlimentar; //g

    private Long colesterol; //mg
    private Long ferro; //mg
    private Long calcio; //mg
    private Long sodio; //mg
    private Long magnesio; //mg
    private Long potassio; //mg
    private Long manganes; //mg
    private Long fosforo; //mg
    private Long cobre; //mg
    private Long zinco; //mg
    private Long niacina; //mg

    @Column(name = "vitaminaC")
    private Long vitaminaC; //Tr

    public Alimento(String nome, Float calorias, Float proteina, Float gordura, Float carboidrato, Float fibraAlimentar) {
        this.nome = nome;
        this.calorias = calorias;
        this.proteina = proteina;
        this.gordura = gordura;
        this.carboidrato = carboidrato;
        this.fibraAlimentar = fibraAlimentar;
    }

    public void atualizarInformacoes(EditarAlimentoDTO dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.calorias() != null) {
            this.calorias = dados.calorias();
        }
        if (dados.proteina() != null) {
            this.proteina = dados.proteina();
        }
        if (dados.gordura() != null) {
            this.gordura = dados.gordura();
        }
        if (dados.carboidrato() != null) {
            this.carboidrato = dados.carboidrato();
        }
        if (dados.fibraAlimentar() != null) {
            this.fibraAlimentar = dados.fibraAlimentar();
        }
        if (dados.colesterol() != null) {
            this.colesterol = dados.colesterol();
        }
        if (dados.ferro() != null) {
            this.ferro = dados.ferro();
        }
        if (dados.calcio() != null) {
            this.calcio = dados.calcio();
        }
        if (dados.sodio() != null) {
            this.sodio = dados.sodio();
        }
        if (dados.magnesio() != null) {
            this.magnesio = dados.magnesio();
        }
        if (dados.potassio() != null) {
            this.potassio = dados.potassio();
        }
        if (dados.manganes() != null) {
            this.manganes = dados.manganes();
        }
        if (dados.fosforo() != null) {
            this.fosforo = dados.fosforo();
        }
        if (dados.cobre() != null) {
            this.cobre = dados.cobre();
        }
        if (dados.zinco() != null) {
            this.zinco = dados.zinco();
        }
        if (dados.niacina() != null) {
            this.niacina = dados.niacina();
        }
        if (dados.vitaminaC() != null) {
            this.vitaminaC = dados.vitaminaC();
        }
    }

}
