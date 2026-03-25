package com.dtplan.controller;

import com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.ficha.FichaService;
import com.dtplan.domain.ficha.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/fichas")
public class FichaController {
	
	@Autowired
	private FichaRepository treinoRepository;
	@Autowired
	private FichaRepository fichaRepository;

	@Autowired
	private FichaService fichaService;

	@PostMapping("/criar")
	public ResponseEntity<DetalharFichaDTO> cadastrar(@RequestBody CadastrarFichaDTO dados, UriComponentsBuilder uriBuilder) {
		var dto = fichaService.cadastrarFicha(dados);

		return ResponseEntity.ok(dto);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity <DetalharFichaDTO> editar(@PathVariable long id, @RequestBody EditarFichaDTO dados) {
		var dto =  fichaService.editarFicha(id, dados);

		return ResponseEntity.ok(dto);
	}
	@PutMapping("/reordenar-exercicios")
	public ResponseEntity<Void> reordenarExercicios(@RequestBody ReordenarExerciciosDTO dados) {
		fichaService.reordenarExercicios(dados.fichaId(), dados.novaOrdemIds());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/listar")
		public ResponseEntity<List<ListarFichaDTO>> listar(@PageableDefault(size = 10) @RequestParam long treinoId) {
		var dto = fichaService.listarFichas(treinoId);

		return ResponseEntity.ok(dto);
    }
	@GetMapping("/detalhar/{id}")
	public ResponseEntity<DetalharFichaDTO> detalhar(@PathVariable long id) {
		var dto = fichaService.detalharFicha(id);

		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Void> excluir(@PathVariable long id) {
		fichaService.excluirFicha(id);

		return ResponseEntity.noContent().build();
	}
}
