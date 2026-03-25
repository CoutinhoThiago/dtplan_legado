package com.dtplan.controller;

import com.dtplan.domain.alimento.dto.ListarAlimentosDTO;
import com.dtplan.domain.dieta.DietaRepository;
import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.ficha.FichaService;
import com.dtplan.domain.ficha.dto.CadastrarFichaDTO;
import com.dtplan.domain.ficha.dto.DetalharFichaDTO;
import com.dtplan.domain.ficha.dto.EditarFichaDTO;
import com.dtplan.domain.ficha.dto.ListarFichaDTO;
import com.dtplan.domain.refeicao.RefeicaoRepository;
import com.dtplan.domain.refeicao.RefeicaoService;
import com.dtplan.domain.refeicao.dto.CadastrarRefeicaoDTO;
import com.dtplan.domain.refeicao.dto.DetalharRefeicaoDTO;
import com.dtplan.domain.refeicao.dto.ListarRefeicaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/refeicoes")
public class RefeicaoController {
	
	@Autowired
	private DietaRepository dietaRepository;
	@Autowired
	private RefeicaoRepository refeicoesRepository;

	@Autowired
	private RefeicaoService refeicaoService;

	@PostMapping
	public ResponseEntity<DetalharRefeicaoDTO> cadastrar(@RequestBody CadastrarRefeicaoDTO dados, UriComponentsBuilder uriBuilder) {
		var dto = refeicaoService.cadastrar(dados); // Certifique-se de que há um método 'cadastrar' no serviço
		return ResponseEntity.ok(dto);
	}


	@GetMapping("/listar")
	public ResponseEntity<Page<ListarRefeicaoDTO>> listarTodas(@PageableDefault(size = 10) Pageable paginacao) {
		var dto = refeicaoService.listarRefeicoes(paginacao);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/detalhar/{id}")
	public ResponseEntity<DetalharRefeicaoDTO> detalhar(@PathVariable long id) {
		var dto = refeicaoService.detalharRefeicao(id);
		return ResponseEntity.ok(dto);
	}
}
