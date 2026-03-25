package com.dtplan.domain.exercicio;

import com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import com.dtplan.domain.exercicio.dto.ListarExerciciosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class ExercicioService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Transactional
    public DetalharExercicioDTO cadastrarExercicio(CadastrarExercicioDTO dados) {
        Exercicio novoExercicio = new Exercicio(dados);
        exercicioRepository.save(novoExercicio);
        return new DetalharExercicioDTO(novoExercicio);
    }

    public Page<ListarExerciciosDTO> listarExercicios(Pageable paginacao) {
        return exercicioRepository.findAll(paginacao).map(ListarExerciciosDTO::new);
    }

    public DetalharExercicioDTO detalharExercicio(long id) {
        Exercicio exercicio = exercicioRepository.getReferenceById(id);
        return new DetalharExercicioDTO(exercicio);
    }

    @Transactional
    public DetalharExercicioDTO atualizarExercicio(long id, EditarExercicioDTO dados) {
        Exercicio exercicio = exercicioRepository.getReferenceById(id);
        exercicio.atualizarInformacoes(dados);
        return new DetalharExercicioDTO(exercicio);
    }

    @Transactional
    public void inativarExercicio(long id) {
        Exercicio exercicio = exercicioRepository.getReferenceById(id);
        exercicio.inativar();
        exercicioRepository.save(exercicio);
    }

    @Transactional
    public void excluirExercicio(long id) {
        exercicioRepository.deleteById(id);
    }

    public List<Exercicio> buscarExerciciosPorIds(List<Long> ids) {
        return exercicioRepository.findAllById(ids);
    }
}

