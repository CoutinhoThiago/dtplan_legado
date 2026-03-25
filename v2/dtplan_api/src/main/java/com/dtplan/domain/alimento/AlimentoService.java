package com.dtplan.domain.alimento;

import com.dtplan.domain.alimento.dto.CadastrarAlimentoDTO;
import com.dtplan.domain.alimento.dto.DetalharAlimentoDTO;
import com.dtplan.domain.alimento.dto.EditarAlimentoDTO;
import com.dtplan.domain.alimento.dto.ListarAlimentosDTO;
import com.dtplan.domain.dieta.Dieta;
import com.dtplan.domain.dieta.DietaRepository;
import com.dtplan.domain.dieta.dto.CadastrarDietaDTO;
import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import com.dtplan.domain.exercicio.dto.ListarExerciciosDTO;
import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicao.RefeicaoRepository;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;
    @Autowired
    private DietaRepository dietaRepository;
    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Transactional
    public DetalharAlimentoDTO cadastrarDieta(CadastrarAlimentoDTO dados) {
        Alimento alimento = new Alimento(dados.nome(), dados.calorias(), dados.proteina(), dados.gordura(), dados.carboidrato(), dados.fibraAlimentar());

        alimentoRepository.save(alimento);

        return new DetalharAlimentoDTO(alimento);
    }

    public Page<ListarAlimentosDTO> listarAlimentos(Pageable paginacao) {
        return alimentoRepository.findAll(paginacao).map(ListarAlimentosDTO::new);
    }

    public DetalharAlimentoDTO detalharAlimento(long id) {
        Alimento alimento = alimentoRepository.getReferenceById(id);
        return new DetalharAlimentoDTO(alimento);
    }

    public DetalharAlimentoDTO editarAlimento(long id, EditarAlimentoDTO dados) {
        Optional<Alimento> alimentoOpt = alimentoRepository.findById(id);
        Alimento alimento = alimentoOpt.get();

        alimento.atualizarInformacoes(dados);

        alimentoRepository.save(alimento);

        //List<Refeicao> refeicoes = refeicaoRepository.findAllByAlimento(alimento);
        //List<Dieta> dietas = dietaRepository.findAllByAlimento(alimento);

        return new DetalharAlimentoDTO(alimento);
    }
}
