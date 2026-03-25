package com.dtplan.controller;

import com.dtplan.domain.alimento.AlimentoService;
import com.dtplan.domain.alimento.dto.DetalharAlimentoDTO;
import com.dtplan.domain.alimento.dto.ListarAlimentosDTO;
import com.dtplan.domain.exercicio.ExercicioService;
import com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import com.dtplan.domain.exercicio.dto.ListarExerciciosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping
    public ResponseEntity<Page<ListarAlimentosDTO>> listar(@PageableDefault(size = 200, sort = {"nome"}) Pageable paginacao) {
        var page = alimentoService.listarAlimentos(paginacao);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalharAlimentoDTO> detalhar(@PathVariable long id) {
        var dto = alimentoService.detalharAlimento(id);

        return ResponseEntity.ok(dto);
    }
}
