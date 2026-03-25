package com.dtplan.controller;

import com.dtplan.domain.execucao.Execucao;
import com.dtplan.domain.execucao.ExecucaoService;
import com.dtplan.domain.execucao.dto.AtualizacaoExecucaoDTO;
import com.dtplan.domain.execucao.dto.CadastroExecucaoDTO;
import com.dtplan.domain.execucao.dto.DetalharExecucaoDTO;
import com.dtplan.domain.execucao.dto.ListarExecucaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/execucoes")
public class ExecucaoController {

    private final ExecucaoService execucaoService;

    public ExecucaoController(ExecucaoService execucaoService) {
        this.execucaoService = execucaoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<DetalharExecucaoDTO> cadastrar(@RequestBody CadastroExecucaoDTO dto) {
        var execucao = execucaoService.cadastrar(dto);
        return ResponseEntity.ok(execucao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalharExecucaoDTO> atualizar(@PathVariable Long id, @RequestBody AtualizacaoExecucaoDTO dto) {
        var execucao = execucaoService.atualizar(id, dto);
        return ResponseEntity.ok(execucao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        execucaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-ficha/{fichaId}")
    public ResponseEntity<List<DetalharExecucaoDTO>> listarPorFicha(@PathVariable Long fichaId) {
        return ResponseEntity.ok(execucaoService.listarPorFicha(fichaId));
    }

    @GetMapping("/listar/{fichaId}")
    public ResponseEntity<List<ListarExecucaoDTO>> listarPorFichaId(@PathVariable Long fichaId) {
        List<Execucao> execucoes = execucaoService.listarPorFichaId(fichaId);
        List<ListarExecucaoDTO> dtos = execucoes.stream()
                .map(ListarExecucaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/por-treino/{treinoId}")
    public ResponseEntity<List<DetalharExecucaoDTO>> listarPorTreino(@PathVariable Long treinoId) {
        return ResponseEntity.ok(execucaoService.listarPorTreino(treinoId));
    }
}
