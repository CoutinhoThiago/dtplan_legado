package com.dtplan.domain.exercicio;

import com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import com.dtplan.domain.exercicio.dto.ListarExerciciosDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Table(name = "exercicios")
@Entity(name = "Exercicio")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Exercicio {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String observacoes;
    private boolean ativo;
    private Integer tipo;

    private String musculo_alvo;



    public Exercicio(CadastrarExercicioDTO dados) {
        this.nome = dados.nome();
        this.ativo = true;
        //this.ativo = dados.ativo();
        this.tipo = dados.tipo();
        this.observacoes = dados.observacoes();

        this.musculo_alvo = dados.musculo_alvo();
    }

    public void atualizarInformacoes(EditarExercicioDTO dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
    }

    public void inativar() {
        this.ativo = false;
    }
}
