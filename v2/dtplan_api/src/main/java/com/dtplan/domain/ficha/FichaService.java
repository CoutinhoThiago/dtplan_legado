package com.dtplan.domain.ficha;

import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.exercicio.ExercicioRepository;
import com.dtplan.domain.ficha.dto.CadastrarFichaDTO;
import com.dtplan.domain.ficha.dto.DetalharFichaDTO;
import com.dtplan.domain.ficha.dto.EditarFichaDTO;
import com.dtplan.domain.ficha.dto.ListarFichaDTO;
import com.dtplan.domain.fichaExercicio.FichaExercicio;
import com.dtplan.domain.fichaExercicio.FichaExercicioRepository;
import com.dtplan.domain.fichaExercicio.dto.EditarFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.FichaExercicioDTO;
import com.dtplan.domain.treino.Treino;
import com.dtplan.domain.treino.TreinoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FichaService {

    @Autowired
    ExercicioRepository exercicioRepository;

    @Autowired
    FichaRepository fichaRepository;
    @Autowired
    FichaExercicioRepository fichaExercicioRepository;

    @Autowired
    TreinoRepository treinoRepository;

    @Transactional
    public DetalharFichaDTO cadastrarFicha(CadastrarFichaDTO dados) {
        // Busca o treino no repositório
        Optional<Treino> treinoOpt = treinoRepository.findById(dados.treino());
        if (treinoOpt.isEmpty()) {
            throw new RuntimeException("Treino não encontrado");
        }
        Treino treino = treinoOpt.get();

        // Cria a ficha com a lista de exercícios inicializada
        Ficha ficha = new Ficha(dados.nome(), treino);
        ficha.setFichaExercicios(new ArrayList<>()); // Inicializa a lista de exercícios

        // Salva a ficha no repositório
        fichaRepository.save(ficha);

        // Retorna o DTO detalhando a ficha criada
        return new DetalharFichaDTO(ficha);
    }

    @Transactional
    public DetalharFichaDTO editarFicha(long id, EditarFichaDTO dados) {
        // Busca a ficha no repositório
        Optional<Ficha> fichaOpt = fichaRepository.findById(id);
        System.out.println("ficha");
        if (fichaOpt.isEmpty()) {
            throw new RuntimeException("Ficha não encontrada");
        }
        Ficha ficha = fichaOpt.get();
        System.out.println("ficha");

        // Atualiza o nome da ficha
        ficha.setNome(dados.nome());

        // Limpa os exercícios existentes (exclui todos os FichaExercicio associados à ficha)
        fichaExercicioRepository.deleteByFichaId(id); // Moveu para fora do loop

        // Adiciona os novos exercícios
        for (EditarFichaExercicioDTO exercicioDTO : dados.fichaExercicios()) {
            // Busca o exercício no repositório
            Optional<Exercicio> exercicioOpt = exercicioRepository.findById(exercicioDTO.exercicioId());
            if (exercicioOpt.isEmpty()) {
                throw new RuntimeException("Exercício não encontrado com o ID: " + exercicioDTO.exercicioId());
            }
            Exercicio exercicio = exercicioOpt.get();

            // Cria o FichaExercicio com os dados fornecidos
            FichaExercicio fichaExercicio = new FichaExercicio(
                    ficha,
                    exercicio,
                    exercicioDTO.repeticoes(),
                    exercicioDTO.series(),
                    exercicioDTO.carga(),
                    exercicioDTO.duracao_minutos(),
                    exercicioDTO.intensidade(),
                    exercicioDTO.ordem()
            );

            // Salva o FichaExercicio no repositório
            fichaExercicioRepository.save(fichaExercicio);
        }

        // Salva a ficha atualizada
        fichaRepository.save(ficha);

        // Retorna o DTO detalhando a ficha editada
        return new DetalharFichaDTO(ficha);
    }

    public List<ListarFichaDTO> listarFichas(Long trinoId) {
        return fichaRepository.findByTreinoId(trinoId);
    }

    public DetalharFichaDTO detalharFicha(long id) {
        Ficha ficha = fichaRepository.findById(id).orElseThrow(() -> new RuntimeException("Ficha não encontrado"));

        return new DetalharFichaDTO(ficha);
    }

    @Transactional
    public void excluirFicha(Long id) {
        fichaExercicioRepository.deleteByFichaId(id);
        fichaRepository.deleteById(id);
    }

    @Transactional
    public void reordenarExercicios(Long fichaId, List<Long> novaOrdemIds) {
        Ficha ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada"));

        // Atualiza a ordem dos exercícios
        for (int i = 0; i < novaOrdemIds.size(); i++) {
            Long exercicioId = novaOrdemIds.get(i);
            FichaExercicio fichaExercicio = ficha.getFichaExercicios().stream()
                    .filter(fe -> fe.getId().equals(exercicioId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Exercício não encontrado na ficha"));
            fichaExercicio.setOrdem(i); // Define a nova ordem
        }

        fichaRepository.save(ficha); // Salva as alterações
    }
}
