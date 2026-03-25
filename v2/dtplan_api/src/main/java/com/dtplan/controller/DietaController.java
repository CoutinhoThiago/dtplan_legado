package com.dtplan.controller;

import com.dtplan.domain.dieta.Dieta;
import com.dtplan.domain.dieta.DietaRepository;
import com.dtplan.domain.dieta.DietaService;
import com.dtplan.domain.dieta.dto.CadastrarDietaDTO;
import com.dtplan.domain.dieta.dto.DetalharDietaDTO;
import com.dtplan.domain.dieta.dto.EditarDietaDTO;
import com.dtplan.domain.dieta.dto.ListarDietaDTO;
import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicao.RefeicaoRepository;
import com.dtplan.domain.refeicao.dto.ListarRefeicaoDTO;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimentoRepository;
import com.dtplan.domain.refeicaoAlimento.dto.RefeicaoAlimentoDTO;
import com.dtplan.domain.treino.dto.ListarTreinoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dietas")
public class DietaController {

	@Autowired
	private DietaRepository dietaRepository;
	@Autowired
	private RefeicaoRepository refeicaoRepository;

	@Autowired
	private DietaService dietaService;

	@Autowired
	private RefeicaoAlimentoRepository refeicaoAlimentoRepository;


	@PostMapping("/criar")
	public ResponseEntity<CadastrarDietaDTO> cadastrar(@RequestBody CadastrarDietaDTO dados, UriComponentsBuilder uriBuilder) {
		var dto = dietaService.cadastrarDieta(dados);

		return ResponseEntity.ok(dto);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<EditarDietaDTO> editar(@PathVariable long id, @RequestBody EditarDietaDTO dados) {
		var dto = dietaService.editarDieta(id, dados);

		return ResponseEntity.ok(dto);
	}

	@GetMapping("/detalhar/{id}")
	public ResponseEntity<?> detalhar(@PathVariable long id) {
		// Busca a dieta pelo ID
		Dieta dieta = dietaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Dieta não encontrada"));

		// Busca as refeições associadas à dieta
		List<ListarRefeicaoDTO> refeicoes = refeicaoRepository.findByDietaId(dieta.getId()).stream()
				.map(refeicao -> {
					// Busca os alimentos associados à refeição
					List<RefeicaoAlimento> refeicaoAlimentos = refeicaoAlimentoRepository.findByRefeicaoId(refeicao.getId());

					// Mapeia RefeicaoAlimento para RefeicaoAlimentoDTO
					List<RefeicaoAlimentoDTO> refeicaoAlimentosDTO = refeicaoAlimentos.stream()
							.map(RefeicaoAlimentoDTO::new)
							.toList();

					// Retorna o ListarRefeicaoDTO com os alimentos associados
					return new ListarRefeicaoDTO(refeicao, refeicaoAlimentosDTO);
				})
				.toList();

		// Cria o DTO detalhado da dieta
		DetalharDietaDTO detalharDieta = new DetalharDietaDTO(dieta, refeicoes);

		return ResponseEntity.ok(detalharDieta);
	}

	@GetMapping("/listar")
	public ResponseEntity<Page<ListarDietaDTO>> listar(
			//@RequestParam Long usuarioId, // Parâmetro para filtrar por ID do usuário
			@RequestHeader("Authorization") String authorizationHeader,
			@PageableDefault(size = 10) Pageable paginacao
	) {
		var page = dietaService.listarDietas(authorizationHeader, paginacao);

		return ResponseEntity.ok(page);
	}
}
