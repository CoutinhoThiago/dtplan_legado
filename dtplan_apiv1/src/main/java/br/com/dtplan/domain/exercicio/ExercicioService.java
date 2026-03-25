package br.com.dtplan.domain.exercicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExercicioService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    public List<Exercicio> buscarExerciciosPorIds(List<Long> ids) {
        return exercicioRepository.findAllById(ids);
    }
}