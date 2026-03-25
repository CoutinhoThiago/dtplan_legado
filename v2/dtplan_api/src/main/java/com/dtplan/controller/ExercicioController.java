package com.dtplan.controller;

import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.exercicio.ExercicioService;
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

import com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import com.dtplan.domain.exercicio.dto.ListarExerciciosDTO;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioService exercicioService;

    @PostMapping("/criar")
    public ResponseEntity<DetalharExercicioDTO> cadastrar(@RequestBody CadastrarExercicioDTO dados, UriComponentsBuilder uriBuilder) {
        var dto = exercicioService.cadastrarExercicio(dados);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<DetalharExercicioDTO> editar(@PathVariable long id, @RequestBody EditarExercicioDTO dados) {
        var dto = exercicioService.atualizarExercicio(id, dados);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<Void> inativar(@PathVariable long id) {
        exercicioService.inativarExercicio(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        exercicioService.excluirExercicio(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ListarExerciciosDTO>> listar(@PageableDefault(size = 200, sort = {"nome"}) Pageable paginacao) {
        var page = exercicioService.listarExercicios(paginacao);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/detalhar/{id}")
    public ResponseEntity<DetalharExercicioDTO> detalhar(@PathVariable long id) {
        var dto = exercicioService.detalharExercicio(id);

        return ResponseEntity.ok(dto);
    }
}
