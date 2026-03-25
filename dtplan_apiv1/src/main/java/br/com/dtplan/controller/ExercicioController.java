package br.com.dtplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import br.com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import br.com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import br.com.dtplan.domain.exercicio.dto.ListarExerciciosDTO;
import br.com.dtplan.domain.exercicio.Exercicio;
import br.com.dtplan.domain.exercicio.ExercicioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {
	
	@Autowired
	private ExercicioRepository repository;

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody CadastrarExercicioDTO dados, UriComponentsBuilder uriBuilder) {
		var exercicio = new Exercicio(dados);
		repository.save(exercicio);

		var uri = uriBuilder.path("exercicio/{id}").buildAndExpand(exercicio.getId()).toUri();
		var dto = new DetalharExercicioDTO(exercicio);

		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping
    public ResponseEntity<Page<ListarExerciciosDTO>> listar(@PageableDefault(size = 200, sort = {"descricao"}) Pageable paginacao) {
		var page = repository.findAll(paginacao).map(ListarExerciciosDTO::new);
		
		return ResponseEntity.ok(page);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable long id) {
		var exercicio = repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DetalharExercicioDTO(exercicio));
    }
	
	@PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid EditarExercicioDTO dados) {
		var exercicio = repository.getReferenceById(dados.id());
		exercicio.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DetalharExercicioDTO(exercicio));
    }
	
	@PutMapping ("/{id}")
    @Transactional
    public ResponseEntity inativar(@PathVariable long id) {
		var exercicio = repository.getReferenceById(id);
		exercicio.inativar();
		
		return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable long id) {
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}