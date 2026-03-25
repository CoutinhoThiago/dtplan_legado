package com.dtplan.domain.dieta;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.dieta.dto.CadastrarDietaDTO;
import com.dtplan.domain.dieta.dto.EditarDietaDTO;
import com.dtplan.domain.dieta.dto.ListarDietaDTO;
import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicao.RefeicaoRepository;
import com.dtplan.domain.refeicao.dto.ListarRefeicaoDTO;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimentoRepository;
import com.dtplan.domain.refeicaoAlimento.dto.RefeicaoAlimentoDTO;
import com.dtplan.domain.treino.Treino;
import com.dtplan.domain.treino.dto.ListarTreinoDTO;
import com.dtplan.domain.usuario.Usuario;
import com.dtplan.domain.usuario.UsuarioRepository;
import com.dtplan.domain.usuario.UsuarioService;
import com.dtplan.infra.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class DietaService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DietaRepository dietaRepository;
    @Autowired
    private RefeicaoRepository refeicaoRepository;
    @Autowired
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;


    @Transactional
    public CadastrarDietaDTO cadastrarDieta(CadastrarDietaDTO dados) {
        Dieta dieta = new Dieta(dados);

        dietaRepository.save(dieta);

        return new CadastrarDietaDTO(dieta.getDescricao(), dieta.getAutor(), dieta.getTipo(), dieta.getUsuario());
    }

    @Transactional
    public EditarDietaDTO editarDieta(long id, EditarDietaDTO dados) {

        Optional<Dieta> dietaOpt = dietaRepository.findById(id);
        Dieta dieta = dietaOpt.get();

        dieta.atualizaInformacoes(dados);

        dietaRepository.save(dieta);

        return new EditarDietaDTO(dieta.getDescricao(), dieta.getAutor(), dieta.getTipo(), dieta.getUsuario(), dieta.getCalorias(), dieta.getProteina(), dieta.getGordura(), dieta.getCarboidrato(), dieta.getFibraAlimentar());
    }

    @Transactional
    public Page<ListarDietaDTO> listarDietas(String authorizationHeader, Pageable paginacao) {
        // Obtém o usuário a partir do token de autorização
        Usuario usuario = usuarioService.obterUsuarioDoToken(authorizationHeader);

        // Busca as dietas do usuário
        Page<Dieta> dietasPage = dietaRepository.findByUsuarioId(usuario.getId(), paginacao);

        // Mapeia as dietas para ListarDietaDTO
        return dietasPage.map(this::mapearDietaParaDTO);
    }


    // Método para mapear uma Dieta para ListarDietaDTO
    private ListarDietaDTO mapearDietaParaDTO(Dieta dieta) {
        // Busca as refeições associadas à dieta
        List<ListarRefeicaoDTO> refeicoesDTO = buscarRefeicoesDaDieta(dieta);

        // Retorna o DTO da dieta com as refeições associadas
        return new ListarDietaDTO(dieta, refeicoesDTO);
    }

    // Método para buscar as refeições de uma dieta e mapeá-las para ListarRefeicaoDTO
    private List<ListarRefeicaoDTO> buscarRefeicoesDaDieta(Dieta dieta) {
        List<Refeicao> refeicoes = refeicaoRepository.findByDietaId(dieta.getId());

        return refeicoes.stream()
                .map(this::mapearRefeicaoParaDTO)
                .toList();
    }

    // Método para mapear uma Refeicao para ListarRefeicaoDTO
    private ListarRefeicaoDTO mapearRefeicaoParaDTO(Refeicao refeicao) {
        // Busca os alimentos associados à refeição
        List<RefeicaoAlimentoDTO> refeicaoAlimentosDTO = buscarAlimentosDaRefeicao(refeicao);

        // Retorna o DTO da refeição com os alimentos associados
        return new ListarRefeicaoDTO(refeicao, refeicaoAlimentosDTO);
    }

    // Método para buscar os alimentos de uma refeição e mapeá-los para RefeicaoAlimentoDTO
    private List<RefeicaoAlimentoDTO> buscarAlimentosDaRefeicao(Refeicao refeicao) {
        List<RefeicaoAlimento> refeicaoAlimentos = refeicaoAlimentoRepository.findByRefeicaoId(refeicao.getId());

        return refeicaoAlimentos.stream()
                .map(RefeicaoAlimentoDTO::new)
                .toList();
    }
}

