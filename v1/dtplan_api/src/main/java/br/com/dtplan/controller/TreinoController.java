package br.com.dtplan.controller;

import java.util.*;

import br.com.dtplan.domain.exercicio.ExercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.dtplan.domain.exercicio.Exercicio;
import br.com.dtplan.domain.treino.DadosCadastroTreino;
import br.com.dtplan.domain.treino.DadosDetalhamentoTreino;
import br.com.dtplan.domain.treino.DadosListagemTreino;
import br.com.dtplan.domain.treino.Treino;
import br.com.dtplan.domain.treino.TreinoRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/treinos")
public class TreinoController {
	
	@Autowired
	private TreinoRepository repository;

	@Autowired
	private ExercicioService exercicioService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroTreino dados, UriComponentsBuilder uriBuilder) {
		List<Long> exercicioIds = dados.exercicios_id();
		List<Exercicio> exercicios = exercicioService.buscarExerciciosPorIds(exercicioIds);

		for (Long id : exercicioIds) {
			Exercicio exercicioEncontrado = null;
			for (Exercicio ex : exercicios) {
				if (ex.getId() == id) {
					exercicioEncontrado = ex;
					break;
				}
			}

			if (exercicioEncontrado == null) {
				// Exercício não existe
				return ResponseEntity.badRequest().body("Exercício com ID " + id + " não existe.");
			} else if (!exercicioEncontrado.isAtivo()) {
				// Exercício existe, mas está inativo
				return ResponseEntity.badRequest().body("Exercício com ID " + id + " está inativo.");
			}
		}

		Treino treino = new Treino(dados);
		treino.adicionarExercicios(exercicios);

		repository.save(treino);

		var uri = uriBuilder.path("/treinos/{id}").buildAndExpand(treino.getId()).toUri();
		var dto = new DadosDetalhamentoTreino(treino, exercicios);

		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping
    public ResponseEntity<Page<DadosListagemTreino>> listar(@PageableDefault(size = 10) Pageable paginacao) {
		var page = repository.findAll(paginacao).map(DadosListagemTreino::new);
		
		return ResponseEntity.ok(page);
    }

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable long id) {
		Treino treino = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Treino não encontrado"));
		DadosListagemTreino detalhesTreino = new DadosListagemTreino(treino);
		return ResponseEntity.ok(detalhesTreino);
	}
}
