package com.dtplan.domain.refeicao;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.alimento.AlimentoRepository;
import com.dtplan.domain.dieta.Dieta;
import com.dtplan.domain.dieta.DietaRepository;
import com.dtplan.domain.refeicao.dto.CadastrarRefeicaoDTO;
import com.dtplan.domain.refeicao.dto.DetalharRefeicaoDTO;
import com.dtplan.domain.refeicao.dto.EditarRefeicaoDTO;
import com.dtplan.domain.refeicao.dto.ListarRefeicaoDTO;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimentoRepository;
import com.dtplan.domain.refeicaoAlimento.dto.RefeicaoAlimentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefeicaoService {

    @Autowired
    RefeicaoRepository refeicoesRepository;

    @Autowired
    DietaRepository dietaRepository;

    @Autowired
    AlimentoRepository alimentoRepository;

    @Autowired
    RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    public Page<ListarRefeicaoDTO> listarRefeicoes(Pageable paginacao) {
        // Busca todas as refeições paginadas
        Page<Refeicao> refeicoesPage = refeicoesRepository.findAll(paginacao);

        // Para cada refeição, busca os alimentos associados e cria um ListarRefeicaoDTO
        return refeicoesPage.map(refeicao -> {
            List<RefeicaoAlimento> refeicaoAlimentos = refeicaoAlimentoRepository.findByRefeicaoId(refeicao.getId());
            List<RefeicaoAlimentoDTO> refeicaoAlimentosDTO = refeicaoAlimentos.stream()
                    .map(RefeicaoAlimentoDTO::new)
                    .toList();

            return new ListarRefeicaoDTO(refeicao, refeicaoAlimentosDTO);
        });
    }

    public DetalharRefeicaoDTO cadastrar(CadastrarRefeicaoDTO dados) {
        Dieta dieta = dietaRepository.findById(dados.dieta()).orElseThrow(() -> new RuntimeException("Dieta não encontrada!"));
        Refeicao refeicao = new Refeicao(dados.descricao(), dieta);

        for (RefeicaoAlimento refeicaoAlimento : dados.refeicaoAlimentos()) {
            Optional<Alimento> alimentoOpt = alimentoRepository.findById(refeicaoAlimento.getId());
            Alimento alimento = alimentoOpt.get();
            double quantidade = refeicaoAlimento.getQuantidade();
            refeicao.adicionarAlimento(refeicao, alimento, quantidade);
        }

        Refeicao refeicaoSalva = refeicoesRepository.save(refeicao);

        List<RefeicaoAlimento> refeicaoAlimentos = refeicaoAlimentoRepository.findByRefeicaoId(refeicao.getId());
        List<RefeicaoAlimentoDTO> refeicaoAlimentosDTO = refeicaoAlimentos.stream()
                .map(RefeicaoAlimentoDTO::new)
                .toList();

        // Retorna um DTO com os detalhes da refeição criada
        return new DetalharRefeicaoDTO(refeicaoSalva, refeicaoAlimentosDTO);
    }

    public DetalharRefeicaoDTO detalharRefeicao(Long id) {
        // Busca a refeição pelo ID
        Optional<Refeicao> refeicaoOpt = refeicoesRepository.findById(id);
        if (refeicaoOpt.isPresent()) {
            Refeicao refeicao = refeicaoOpt.get();

            // Busca os alimentos associados à refeição
            List<RefeicaoAlimento> refeicaoAlimentos = refeicaoAlimentoRepository.findByRefeicaoId(refeicao.getId());

            // Mapeia RefeicaoAlimento para RefeicaoAlimentoDTO
            List<RefeicaoAlimentoDTO> refeicaoAlimentosDTO = refeicaoAlimentos.stream()
                    .map(RefeicaoAlimentoDTO::new)
                    .toList();

            // Retorna o DTO detalhado da refeição
            return new DetalharRefeicaoDTO(refeicao, refeicaoAlimentosDTO);
        } else {
            throw new RuntimeException("Refeição não encontrada."); // ou use uma exceção personalizada
        }
    }

    public void editar(Long id, EditarRefeicaoDTO dados) {
        // Busca a refeição pelo ID
        Optional<Refeicao> refeicaoOpt = refeicoesRepository.findById(id);
        if (refeicaoOpt.isPresent()) {
            Refeicao refeicao = refeicaoOpt.get();

            // Processa a lista de alimentos para adicionar ou atualizar
            List<Long> novosIds = dados.refeicaoAlimentos().stream()
                    .map(RefeicaoAlimento::getId)
                    .toList();

            // Remove os alimentos que não estão na nova lista
            refeicao.getRefeicaoAlimentos().removeIf(ra -> !novosIds.contains(ra.getAlimento().getId()));

            // Adiciona ou atualiza os alimentos
            for (RefeicaoAlimento refeicaoAlimento : dados.refeicaoAlimentos()) {
                Alimento alimento = alimentoRepository.findById(refeicaoAlimento.getId())
                        .orElseThrow(() -> new RuntimeException("Alimento não encontrado!"));

                double quantidade = refeicaoAlimento.getQuantidade();

                // Adiciona o alimento na refeição, atualizando a quantidade
                refeicao.adicionarAlimento(refeicao, alimento, quantidade);
            }

            // Atualiza a descrição da refeição, se necessário
            refeicao.setDescricao(dados.descricao());

            // Salva as alterações da refeição no repositório
            refeicoesRepository.save(refeicao);
        } else {
            throw new RuntimeException("Refeição não encontrada."); // ou use uma exceção personalizada
        }
    }

    public void deletar(Long id) {
        // Verifica se a refeição existe
        Optional<Refeicao> refeicaoOpt = refeicoesRepository.findById(id);
        if (refeicaoOpt.isPresent()) {
            Refeicao refeicao = refeicaoOpt.get();

            // Remove todas as associações RefeicaoAlimento vinculadas à refeição
            //refeicao.removerAlimento(refeicao, refeicao.getRefeicaoAlimentos().);

            // Remove a refeição do repositório
            refeicoesRepository.delete(refeicao);
        } else {
            throw new RuntimeException("Refeição não encontrada."); // ou use uma exceção personalizada
        }
    }
}
