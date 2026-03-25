package com.dtplan.domain.execucao;

import com.dtplan.domain.execucao.Execucao;
import com.dtplan.domain.execucao.dto.*;
import com.dtplan.domain.execucaoExercicio.ExecucaoExercicio;
import com.dtplan.domain.execucaoExercicio.ExecucaoExercicioRepository;
import com.dtplan.domain.execucaoExercicio.dto.ExecucaoExercicioDTO;
import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.exercicio.ExercicioRepository;
import com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import com.dtplan.domain.exercicio.dto.ListarExerciciosDTO;
import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.fichaExercicio.FichaExercicio;
import com.dtplan.domain.fichaExercicio.dto.EditarFichaExercicioDTO;
import com.dtplan.domain.treino.Treino;

import com.dtplan.domain.treino.TreinoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExecucaoService {

    private final ExecucaoRepository execucaoRepository;
    private final TreinoRepository treinoRepository;
    private final FichaRepository fichaRepository;
    private final ExercicioRepository exercicioRepository;
    private final ExecucaoExercicioRepository execucaoExercicioRepository;

    public ExecucaoService(
            ExecucaoRepository execucaoRepository,
            TreinoRepository treinoRepository,
            FichaRepository fichaRepository,
            ExercicioRepository exercicioRepository,
            ExecucaoExercicioRepository execucaoExercicioRepository
    ) {
        this.execucaoRepository = execucaoRepository;
        this.treinoRepository = treinoRepository;
        this.fichaRepository = fichaRepository;
        this.exercicioRepository = exercicioRepository;
        this.execucaoExercicioRepository = execucaoExercicioRepository;
    }

    public DetalharExecucaoDTO cadastrar(CadastroExecucaoDTO dto) {
        Treino treino = treinoRepository.findById(dto.treinoId())
                .orElseThrow(() -> new EntityNotFoundException("Treino não encontrado"));

        Ficha ficha = fichaRepository.findById(dto.fichaId())
                .orElseThrow(() -> new EntityNotFoundException("Ficha não encontrada"));

        Execucao execucao = new Execucao(dto.data(), dto.observacao(), treino, ficha);
        execucaoRepository.save(execucao);

        List<ExecucaoExercicio> execucoesExercicios = new ArrayList<>();

        for (ExecucaoExercicioDTO eeDTO : dto.execucaoExercicios()) {
            Exercicio exercicio = exercicioRepository.findById(eeDTO.id())
                    .orElseThrow(() -> new EntityNotFoundException("Exercício não encontrado"));

            ExecucaoExercicio execucaoExercicio = new ExecucaoExercicio();
            execucaoExercicio.setExercicio(exercicio);
            execucaoExercicio.setFicha(ficha);
            execucaoExercicio.setSeriesRealizadas(eeDTO.seriesRealizadas());
            execucaoExercicio.setRepeticoesRealizadas(eeDTO.repeticoesRealizadas());
            execucaoExercicio.setPeso(eeDTO.peso());
            //execucaoExercicio.setObservacao(eeDTO.observacao());

            execucaoExercicio.setExecucao(execucao);
            execucaoExercicio.setExercicio(exercicio);
            execucaoExercicio.setFicha(ficha);

            execucoesExercicios.add(execucaoExercicio);
        }

        execucaoExercicioRepository.saveAll(execucoesExercicios);
        execucao.adicionarExecucoesExercicios(execucoesExercicios);
        System.out.println(execucoesExercicios);

        return new DetalharExecucaoDTO(execucao, execucoesExercicios);
    }

    public DetalharExecucaoDTO atualizar(Long id, AtualizacaoExecucaoDTO dto) {
        Execucao execucao = execucaoRepository.getReferenceById(id);

        if (dto.observacao() != null) {
            execucao.setObservacao(dto.observacao());
        }

        // Persiste alterações
        execucaoRepository.save(execucao);
        return new DetalharExecucaoDTO(execucao, execucao.getExecucaoExercicios());
    }

    public void excluir(Long id) {
        Execucao execucao = execucaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Execução não encontrada"));
        execucaoRepository.delete(execucao);
    }

    public List<DetalharExecucaoDTO> listarPorFicha(Long fichaId) {
        return execucaoRepository.findByFichaId(fichaId).stream()
                .map(execucao -> new DetalharExecucaoDTO(execucao, execucao.getExecucaoExercicios()))
                .toList();
    }
    public List<Execucao> listarPorFichaId(Long fichaId) {
        return execucaoRepository.findByFichaId(fichaId);
    }

    public List<DetalharExecucaoDTO> listarPorTreino(Long treinoId) {
        return execucaoRepository.findByTreinoId(treinoId).stream()
                .map(execucao -> new DetalharExecucaoDTO(execucao, execucao.getExecucaoExercicios()))
                .toList();
    }
}
