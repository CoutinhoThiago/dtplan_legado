package com.dtplan.domain.fichaExercicio;

import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.exercicio.ExercicioRepository;
import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.ficha.dto.DetalharFichaDTO;
import com.dtplan.domain.ficha.dto.EditarFichaDTO;
import com.dtplan.domain.ficha.dto.ListarFichaDTO;
import com.dtplan.domain.fichaExercicio.dto.CadastrarFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.DetalharFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.EditarFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.FichaExercicioDTO;
import com.dtplan.domain.treino.TreinoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FichaExercicioService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private FichaExercicioRepository fichaExercicioRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    @Transactional
    public DetalharFichaExercicioDTO cadastrarFichaExercicio(CadastrarFichaExercicioDTO dados) {
        // Busca o exercício e a ficha no repositório
        Optional<Exercicio> exercicioOpt = exercicioRepository.findById(dados.exercicioId());
        if (exercicioOpt.isEmpty()) {
            throw new RuntimeException("Exercício não encontrado");
        }
        Exercicio exercicio = exercicioOpt.get();

        Optional<Ficha> fichaOpt = fichaRepository.findById(dados.fichaId());
        if (fichaOpt.isEmpty()) {
            throw new RuntimeException("Ficha não encontrada");
        }
        Ficha ficha = fichaOpt.get();

        // Calcula a ordem automaticamente
        int ordem = fichaExercicioRepository.countByFichaId(dados.fichaId()) + 1;

        // Cria o FichaExercicio com a ordem calculada
        FichaExercicio fichaExercicio = new FichaExercicio(
                ficha,
                exercicio,
                dados.repeticoes(),
                dados.series(),
                dados.carga(),
                dados.duracao_minutos(),
                dados.intensidade(),
                ordem // Ordem automática
        );

        // Salva o FichaExercicio no repositório
        fichaExercicioRepository.save(fichaExercicio);

        // Retorna o DTO detalhando a ficha criada
        return new DetalharFichaExercicioDTO(fichaExercicio);
    }



    public List<ListarFichaDTO> listarFichas(Long treinoId) {
        // Retorna a lista de fichas associadas ao treino
        return fichaRepository.findByTreinoId(treinoId);
    }

    public DetalharFichaExercicioDTO detalharFichaExercicio(long id) {
        // Busca o FichaExercicio no repositório
        FichaExercicio fichaExercicio = fichaExercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FichaExercicio não encontrado"));

        // Retorna o DTO detalhando o FichaExercicio
        return new DetalharFichaExercicioDTO(fichaExercicio);
    }

    @Transactional
    public DetalharFichaExercicioDTO editarFichaExercicio(long id, EditarFichaExercicioDTO dados) {
        // Busca o FichaExercicio no repositório
        FichaExercicio fichaExercicio = fichaExercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FichaExercicio não encontrado"));

        // Atualiza as informações do FichaExercicio
        fichaExercicio.atualizarInformacoes(dados);

        // Salva as alterações
        fichaExercicioRepository.save(fichaExercicio);

        // Retorna o DTO detalhando o FichaExercicio atualizado
        return new DetalharFichaExercicioDTO(fichaExercicio);
    }

    @Transactional
    public void excluirFichaExercicio(long id) {
        // Busca o FichaExercicio no repositório
        FichaExercicio fichaExercicio = fichaExercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FichaExercicio não encontrado"));

        // Salva as alterações
        fichaExercicioRepository.delete(fichaExercicio);
    }
}