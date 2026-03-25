package com.dtplan.controller;

import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.fichaExercicio.FichaExercicioService;
import com.dtplan.domain.fichaExercicio.dto.CadastrarFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.DetalharFichaExercicioDTO;
import com.dtplan.domain.refeicao.RefeicaoRepository;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimentoService;
import com.dtplan.domain.refeicaoAlimento.dto.CadastrarRefeicaoAlimentoDTO;
import com.dtplan.domain.refeicaoAlimento.dto.DetalharRefeicaoAlimentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/refeicaoAlimento")
public class RefeicaoAlimentoController {
	
	@Autowired
	private RefeicaoRepository refeicaoRepository;
	@Autowired
	private RefeicaoRepository RefeicaoRepository;

	@Autowired
	private RefeicaoAlimentoService refeicaoAlimentoService;

	@PostMapping("/criar")
	public ResponseEntity<DetalharRefeicaoAlimentoDTO> cadastrar(@RequestBody CadastrarRefeicaoAlimentoDTO dados, UriComponentsBuilder uriBuilder) {
		var dto = refeicaoAlimentoService.cadastrarRefeicaoAlimento(dados);

		return ResponseEntity.ok(dto);
	}

	/*@GetMapping("/listar")
		public ResponseEntity<List<ListarFichaDTO>> listar(@PageableDefault(size = 10) @RequestParam long treinoId) {
		var dto = fichaService.listarFichas(treinoId);

		return ResponseEntity.ok(dto);
    }*/
	@GetMapping("/detalhar/{id}")
	public ResponseEntity<DetalharRefeicaoAlimentoDTO> detalhar(@PathVariable long id) {
		var dto = refeicaoAlimentoService.detalharRefeicaoAlimento(id);

		return ResponseEntity.ok(dto);
	}
}
