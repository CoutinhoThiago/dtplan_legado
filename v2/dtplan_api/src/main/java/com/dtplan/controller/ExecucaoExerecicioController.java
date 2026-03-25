package com.dtplan.controller;

import com.dtplan.domain.execucao.Execucao;
import com.dtplan.domain.execucao.ExecucaoService;
import com.dtplan.domain.execucao.dto.AtualizacaoExecucaoDTO;
import com.dtplan.domain.execucao.dto.CadastroExecucaoDTO;
import com.dtplan.domain.execucao.dto.DetalharExecucaoDTO;
import com.dtplan.domain.execucao.dto.ListarExecucaoDTO;
import com.dtplan.domain.execucaoExercicio.ExecucaoExercicio;
import com.dtplan.domain.execucaoExercicio.ExecucaoExercicioService;
import com.dtplan.domain.execucaoExercicio.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ExecucaoExercicio")
public class ExecucaoExerecicioController {

    private ExecucaoExercicioService execucaoExercicioService;

    @PostMapping("/criar")
    public ResponseEntity<DetalharExecucaoExercicioDTO> cadastrar(@RequestBody CadastrarExecucaoExercicioDTO dto) {
        var execucaoExercicio = execucaoExercicioService.cadastrar(dto);
        return ResponseEntity.ok(execucaoExercicio);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<DetalharExecucaoExercicioDTO> atualizar(@PathVariable Long id, @RequestBody EditarExecucaoExercicioDTO dto) {
        var execucao = execucaoExercicioService.atualizar(id, dto);
        return ResponseEntity.ok(execucao);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        execucaoExercicioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/{execucaiExercicioId}")
    public ResponseEntity<List<ListarExecucaoExercicioDTO>> listar(@PathVariable Long execucaoExercicioId) {
        return ResponseEntity.ok(null);
    }
}
