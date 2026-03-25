package com.dtplan.domain.refeicaoAlimento;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.alimento.AlimentoRepository;
import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.fichaExercicio.FichaExercicio;
import com.dtplan.domain.fichaExercicio.dto.CadastrarFichaExercicioDTO;
import com.dtplan.domain.fichaExercicio.dto.DetalharFichaExercicioDTO;
import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicao.RefeicaoRepository;
import com.dtplan.domain.refeicaoAlimento.dto.CadastrarRefeicaoAlimentoDTO;
import com.dtplan.domain.refeicaoAlimento.dto.DetalharRefeicaoAlimentoDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefeicaoAlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;



    @Transactional
    public DetalharRefeicaoAlimentoDTO cadastrarRefeicaoAlimento(CadastrarRefeicaoAlimentoDTO dados) {
        Optional<Alimento> alimentoOpt = alimentoRepository.findById(dados.alimentoId());
        Alimento alimento = alimentoOpt.get();
        Optional<Refeicao> refeicaoOpt = refeicaoRepository.findById(dados.refeicaoId());
        Refeicao refeicao = refeicaoOpt.get();

        RefeicaoAlimento refeicaoAlimento = new RefeicaoAlimento(
                refeicao,
                alimento,

                dados.quantidade()

        );
        refeicaoAlimentoRepository.save(refeicaoAlimento);

        // Retorna o DTO detalhando a ficha criada
        return new DetalharRefeicaoAlimentoDTO(refeicaoAlimento);
    }

    @Transactional
    public DetalharRefeicaoAlimentoDTO detalharRefeicaoAlimento(long id) {
        RefeicaoAlimento refeicaoAlimento = refeicaoAlimentoRepository.findById(id).orElseThrow(() -> new RuntimeException("RefeicaoAlimento não encontrado"));

        return new DetalharRefeicaoAlimentoDTO(refeicaoAlimento);
    }
}
