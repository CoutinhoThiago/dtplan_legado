package com.dtplan.domain.usuario;

import com.dtplan.domain.usuario.dto.*;
import com.dtplan.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    public ResponseEntity cadastrarUsuario(CadastrarUsuarioDTO dados) {
        if (this.usuarioRepository.findByEmail(dados.email()) != null) {
            String mensagemErro = "Usuário com o login '" + dados.email() + "' já cadastrado.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
        } else {
            String senhaEncriptada = new BCryptPasswordEncoder().encode(dados.senha());
            Permissao permissao = dados.permissao() != null ? Permissao.valueOf(String.valueOf(dados.permissao())) : Permissao.ADMIN;
            Genero genero = dados.genero() != null ? Genero.valueOf(String.valueOf(dados.genero())) : Genero.OUTRO;

            var usuario = new Usuario(
                    dados.email(),
                    senhaEncriptada,
                    permissao,
                    genero,
                    dados.nome(),
                    dados.cpf(),
                    dados.dataNascimento(),
                    dados.altura(),
                    dados.pesoAtual(),
                    dados.tipoUsuario(),
                    dados.cref(),
                    dados.crn()
            );
            usuarioRepository.save(usuario);

            var dto = new DetalharUsuarioDTO(usuario, false, false); // Retorna dados básicos após cadastro
            return ResponseEntity.ok(dto);
        }
    }

    public ResponseEntity<?> detalharUsuario(
            String authorizationHeader,
            boolean completo,
            boolean alunosCompletos,
            boolean pacientesCompletos
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente.");
        }
        Usuario usuario = obterUsuarioDoToken(authorizationHeader);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        if (completo) {
            // Retorna o DTO completo, considerando o tipo de usuário e os parâmetros
            DetalharUsuarioDTO dto = new DetalharUsuarioDTO(usuario, alunosCompletos, pacientesCompletos);
            return ResponseEntity.ok(dto);
        } else {
            // Retorna o DTO básico (sem alunos e pacientes)
            DetalharUsuarioBasicoDTO dto = new DetalharUsuarioBasicoDTO(usuario, false);
            return ResponseEntity.ok(dto);
        }
    }

    public ResponseEntity excluirUsuario(String authorizationHeader) {
        String token = authorizationHeader.substring(7); // Remover "Bearer "
        String usuarioLogin = tokenService.getSubject(token);
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(usuarioLogin);

        usuarioRepository.deleteById(usuario.getId());
        return ResponseEntity.ok("Usuário excluído com sucesso");
    }

    public DetalharUsuarioDTO atualizarDadosUsuario(String authorizationHeader, EditarUsuarioDTO dados) {
        String token = authorizationHeader.substring(7); // Remover "Bearer "
        String usuarioLogin = tokenService.getSubject(token);
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(usuarioLogin);
        usuario.atualizarDados(dados.nome(), dados.cpf(), dados.dataNascimento(), dados.altura(), dados.pesoAtual(), dados.nivelAtividade(), dados.objetivo());

        // Atualiza a lista de alunos (se fornecida)
        if (dados.alunos().isPresent()) {
            List<Long> alunosIds = dados.alunos().get();
            if (!alunosIds.isEmpty()) {
                List<Usuario> listaDeAlunos = usuarioRepository.findAlunosById(alunosIds);
                usuario.setAlunos(listaDeAlunos);
            } else {
                usuario.setAlunos(new ArrayList<>()); // Define uma lista vazia se a lista fornecida estiver vazia
            }
        }

        // Atualiza a lista de pacientes (se fornecida)
        if (dados.pacients().isPresent()) {
            List<Long> pacientesIds = dados.pacients().get();
            if (!pacientesIds.isEmpty()) {
                List<Usuario> listaDePacientes = usuarioRepository.findPacientesById(pacientesIds);
                usuario.setPacientes(listaDePacientes);
            } else {
                usuario.setPacientes(new ArrayList<>()); // Define uma lista vazia se a lista fornecida estiver vazia
            }
        }

        usuarioRepository.save(usuario);
        return new DetalharUsuarioDTO(usuario, false, false); // Retorna dados básicos após atualização
    }

    public Usuario obterUsuarioDoToken(String authorizationHeader) {
        String token = authorizationHeader.substring(7); // Remove "Bearer "
        String login = tokenService.getSubject(token);

        Usuario usuario = usuarioRepository.findByEmail(login);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        return usuario;
    }
}