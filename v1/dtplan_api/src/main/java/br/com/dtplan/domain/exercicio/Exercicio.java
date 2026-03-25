package br.com.dtplan.domain.exercicio;

import br.com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import br.com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "exercicios")
@Entity(name = "Exercicio")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Exercicio {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String descricao;
	private boolean ativo;
	private int tipo;
	
	private String musculo_alvo;
	private int series;
	private int repeticoes_min;
	private int repeticoes_max;
	private double carga;
	
	private int duracao_minutos;
	private int intensidade;
	
	public Exercicio(CadastrarExercicioDTO dados) {
		this.ativo = true;
		this.descricao = dados.descricao();
		this.ativo = dados.ativo();
		this.tipo = dados.tipo();

		this.musculo_alvo = dados.musculo_alvo();
		this.series = dados.series();
		this.repeticoes_min = dados.repeticoes_min();
		this.repeticoes_max = dados.repeticoes_max();
		this.carga = dados.carga();

		this.duracao_minutos = dados.duracao_minutos();
		this.intensidade = dados.intensidade();

	}
	
	public void atualizarInformacoes(EditarExercicioDTO dados) {
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
	}
	
	public void inativar() {
        this.ativo = false;
    }

}
