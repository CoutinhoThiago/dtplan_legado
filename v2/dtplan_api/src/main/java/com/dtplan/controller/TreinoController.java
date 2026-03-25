package com.dtplan.controller;

import com.dtplan.domain.exercicio.ExercicioService;
import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.ficha.dto.ListarFichaDTO;
import com.dtplan.domain.treino.Treino;
import com.dtplan.domain.treino.TreinoService;
import com.dtplan.domain.treino.dto.EditarTreinoDTO;
import com.dtplan.domain.treino.dto.DetalharTreinoDTO;
import com.dtplan.domain.usuario.Usuario;
import com.dtplan.domain.usuario.UsuarioRepository;
import com.dtplan.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.dtplan.domain.treino.dto.CadastroTreinoDTO;
import com.dtplan.domain.treino.dto.ListarTreinoDTO;
import com.dtplan.domain.treino.TreinoRepository;

import java.util.List;

@RestController
@RequestMapping("/treinos")
	public class TreinoController {

		@Autowired
		private TreinoRepository treinoRepository;
	@Autowired
	private FichaRepository fichaRepository;

	@Autowired
	private ExercicioService exercicioService;
	@Autowired
	private TreinoService treinoService;

	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/criar")
	public ResponseEntity<CadastroTreinoDTO> cadastrar(@RequestBody CadastroTreinoDTO dados, @RequestHeader("Authorization") String authorizationHeader) {
		var dto = treinoService.cadastrarTreino(dados);

		return ResponseEntity.ok(dto);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<DetalharTreinoDTO> editar(@PathVariable long id, @RequestBody EditarTreinoDTO dados) {
		var dto = treinoService.editarTreno(id, dados);

		return ResponseEntity.ok(dto);
	}

	@GetMapping("/listar")
    public ResponseEntity<Page<ListarTreinoDTO>> listar(
			//@RequestParam Long usuarioId, // Parâmetro para filtrar por ID do usuário
			@RequestHeader("Authorization") String authorizationHeader,
			@PageableDefault(size = 10) Pageable paginacao
	) {
		var page = treinoService.listarTreinos(authorizationHeader, paginacao);

		System.out.println(page);

		return ResponseEntity.ok(page);
    }

	@GetMapping("/detalhar/{id}")
	public ResponseEntity<?> detalhar(@PathVariable long id) {
		Treino treino = treinoRepository.findById(id).orElseThrow(() -> new RuntimeException("Treino não encontrado"));
		List<ListarFichaDTO> fichas = fichaRepository.findByTreinoId(treino.getId());

		DetalharTreinoDTO detalhesTreino = new DetalharTreinoDTO(treino, fichas);
		return ResponseEntity.ok(detalhesTreino);
	}

	@DeleteMapping("/excluir/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable long id) {
		treinoService.excluirTreino(id);

		return ResponseEntity.noContent().build();
	}
}
