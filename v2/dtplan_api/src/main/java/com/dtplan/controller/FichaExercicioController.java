package com.dtplan.controller;

import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.ficha.FichaService;
import com.dtplan.domain.ficha.dto.CadastrarFichaDTO;
import com.dtplan.domain.ficha.dto.DetalharFichaDTO;
import com.dtplan.domain.ficha.dto.EditarFichaDTO;
import com.dtplan.domain.ficha.dto.ListarFichaDTO;
import com.dtplan.domain.fichaExercicio.FichaExercicioService;
import com.dtplan.domain.fichaExercicio.dto.CadastrarFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.DetalharFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.EditarFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.FichaExercicioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/fichaExercicio")
public class FichaExercicioController {
	
	@Autowired
	private FichaRepository treinoRepository;
	@Autowired
	private FichaRepository fichaRepository;

	@Autowired
	private FichaExercicioService fichaExercicioService;

	@PostMapping("/criar")
	public ResponseEntity<DetalharFichaExercicioDTO> cadastrar(@RequestBody CadastrarFichaExercicioDTO dados, UriComponentsBuilder uriBuilder) {
		var dto = fichaExercicioService.cadastrarFichaExercicio(dados);

		return ResponseEntity.ok(dto);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<DetalharFichaExercicioDTO> editarFichaExercicio(@PathVariable long id, @RequestBody EditarFichaExercicioDTO dados) {
		var dto = fichaExercicioService.editarFichaExercicio(id, dados);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluir(@PathVariable long id) {
		fichaExercicioService.excluirFichaExercicio(id);

		return ResponseEntity.noContent().build();
	}

	/*@GetMapping("/listar")
		public ResponseEntity<List<ListarFichaDTO>> listar(@PageableDefault(size = 10) @RequestParam long treinoId) {
		var dto = fichaService.listarFichas(treinoId);

		return ResponseEntity.ok(dto);
    }*/
	@GetMapping("/detalhar/{id}")
	public ResponseEntity<DetalharFichaExercicioDTO> detalhar(@PathVariable long id) {
		var dto = fichaExercicioService.detalharFichaExercicio(id);

		return ResponseEntity.ok(dto);
	}


}
