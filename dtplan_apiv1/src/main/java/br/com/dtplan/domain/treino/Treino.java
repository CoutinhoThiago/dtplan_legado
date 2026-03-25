package br.com.dtplan.domain.treino;

import java.util.ArrayList;
import java.util.List;

import br.com.dtplan.domain.exercicio.Exercicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "treinos")
@Entity(name = "Treino")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;
    private String autor;
    private String tipo;


    @ManyToMany
    @JoinTable(
            name = "treino_exercicios", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "treino_id"), // Coluna na tabela de junção que aponta para a chave primária da entidade Treino
            inverseJoinColumns = @JoinColumn(name = "exercicio_id") // Coluna na tabela de junção que aponta para a chave primária da entidade Exercicio
    )
    private List<Exercicio> exercicios = new ArrayList<>();

    public Treino(DadosCadastroTreino dados) {
        this.descricao = dados.descricao();
        this.autor = dados.autor();
        this.tipo = dados.tipo();
    }

    public void adicionarExercicios(List<Exercicio> novosExercicios) {
        if (novosExercicios != null) {
            this.exercicios.addAll(novosExercicios);
        }
    }

    public void atualizarInformacoes(String descricao, String autor, String tipo, List<Long> novosExercicios) {
        if (descricao != null) {
            this.descricao = descricao;
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (tipo != null) {
            this.tipo = tipo;
        }
    }
}