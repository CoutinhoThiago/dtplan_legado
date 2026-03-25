package com.dtplan.domain.treino;

import com.dtplan.domain.ficha.Ficha;
import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.fichaExercicio.FichaExercicioRepository;
import com.dtplan.domain.treino.dto.CadastroTreinoDTO;
import com.dtplan.domain.treino.dto.EditarTreinoDTO;
import com.dtplan.domain.treino.dto.DetalharTreinoDTO;
import com.dtplan.domain.treino.dto.ListarTreinoDTO;
import com.dtplan.domain.usuario.Usuario;
import com.dtplan.domain.usuario.UsuarioRepository;
import com.dtplan.domain.usuario.dto.DetalharUsuarioDTO;
import com.dtplan.domain.usuario.dto.UsuarioDTO;
import com.dtplan.infra.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class TreinoService {

    @Autowired
    private TreinoRepository treinoRepository;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private FichaExercicioRepository fichaExercicioRepository;

    @Transactional
    public CadastroTreinoDTO cadastrarTreino(CadastroTreinoDTO dados) {
        Usuario autor = identificarUsuario(dados.autor());
        if (autor == null) {
            throw new RuntimeException("Autor não encontrado com os dados fornecidos.");
        }

        Usuario usuario = identificarUsuario(dados.usuario());
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado com os dados fornecidos.");
        }

        // Cria o treino
        Treino treino = new Treino(dados.nome(), dados.descricao(), autor, usuario);
        treinoRepository.save(treino);

        return new CadastroTreinoDTO(
                treino.getNome(),
                treino.getDescricao(),
                new UsuarioDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getEmail(),
                        autor.getCpf()
                ),
                new UsuarioDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getCpf()
                )
        );
    }

    private Usuario identificarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.id() != null) {
            return usuarioRepository.findById(usuarioDTO.id())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID fornecido."));
        } else if (usuarioDTO.nome() != null) {
            return Optional.ofNullable(usuarioRepository.findByNome(usuarioDTO.nome()))
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o nome fornecido."));
        } else if (usuarioDTO.login() != null) {
            return Optional.ofNullable(usuarioRepository.findByEmail(usuarioDTO.login()))
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o login fornecido."));
        } else if (usuarioDTO.documento() != null) {
            return Optional.ofNullable(usuarioRepository.findByCpf(usuarioDTO.documento()))
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o documento fornecido."));
        }
        throw new RuntimeException("Nenhum dado de usuário foi fornecido.");
    }


    @Transactional
    public DetalharTreinoDTO editarTreno(long id, EditarTreinoDTO dados) {
        Optional<Treino> treinoOpt = treinoRepository.findById(id);
        Treino treino = treinoOpt.get();

        Usuario autor = identificarUsuario(dados.autor());
        Usuario usuario = identificarUsuario(dados.usuario());

        treino.atualizarInformacoes(dados);

        treinoRepository.save(treino);

        return new DetalharTreinoDTO(
                treino.getId(),
                treino.getNome(),
                treino.getDescricao(),
                new DetalharUsuarioDTO(autor, false, false),
                new DetalharUsuarioDTO(usuario, false, false),
                null);
    }

    @Transactional
    public void excluirTreino(long id) {
        Optional<Treino> treinoOpt = treinoRepository.findById(id);
        Treino treino = treinoOpt.get();

        for (Ficha ficha : treino.getFichas()) {
            fichaExercicioRepository.deleteByFichaId(ficha.getId());  // Método para excluir as entradas em ficha_exercicio
        }

        fichaRepository.deleteAll(treino.getFichas());

        treinoRepository.deleteById(id);
    }

    @Transactional
    public Page<ListarTreinoDTO> listarTreinos(String authorizationHeader, Pageable paginacao) {
        // Extrai o token e obtém o email do usuário
        String token = authorizationHeader.substring(7); // Remove "Bearer "
        String login = tokenService.getSubject(token);

        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(login);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        // Busca os treinos do usuário
        Page<Treino> treinos = treinoRepository.findByUsuarioId(usuario.getId(), paginacao);

        // Mapeia os treinos para DTOs
        return treinos.map(treino -> {
            // Obtém o autor do treino
            Usuario autor = usuarioRepository.findByEmail(treino.getAutor().getEmail());

            // Constrói o DTO
            return new ListarTreinoDTO(
                    treino.getId(),
                    treino.getNome(),
                    treino.getDescricao(),
                    new DetalharUsuarioDTO(autor, false, false), // Autor do treino
                    new DetalharUsuarioDTO(usuario, false, false) // Usuário associado ao treino
            );
        });
    }
}