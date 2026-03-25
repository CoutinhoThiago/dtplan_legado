package br.com.dtplan.controller;

import br.com.dtplan.domain.usuario.UsuarioRepository;
import br.com.dtplan.domain.usuario.dto.CadastrarUsuarioDTO;
import br.com.dtplan.domain.usuario.dto.DetalharUsuarioDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import br.com.dtplan.domain.usuario.dto.LoginDTO;
import br.com.dtplan.domain.usuario.Usuario;
import br.com.dtplan.infra.security.DadosTokenJWT;
import br.com.dtplan.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastro(@RequestBody @Valid CadastrarUsuarioDTO dados) {
        if(this.usuarioRepository.findByLogin(dados.login())  != null) {
            String mensagemErro = "Usuário com o login '" + dados.login() + "' já cadastrado.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
        }
        else {
            String senhaEncriptada = new BCryptPasswordEncoder().encode(dados.senha());
            var usuario = new Usuario(
                    dados.login(),
                    senhaEncriptada,
                    //dados.role(),
                    dados.nome(),
                    dados.cpf()
            );
            usuarioRepository.save(usuario);

            var dto = new DetalharUsuarioDTO(usuario);
            System.out.println("Cadastro feito com sucesso");
            return ResponseEntity.ok(dto); //return ResponseEntity.status(HttpStatus.OK).body("Cadastro realizado com sucesso.");
        }
    }


    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid LoginDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        System.out.println(authenticationToken);
        var authentication = manager.authenticate(authenticationToken);

        System.out.println(authentication);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @GetMapping
    public ResponseEntity<?> validarToken(@RequestBody Map<String, String> body) {
        String tokenJWT = body.get("token");

        // Verifique se o token foi fornecido
        if (tokenJWT == null || tokenJWT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token não fornecido");
        }

        boolean isValid = tokenService.isTokenValido(tokenJWT);

        if (isValid) {
            return ResponseEntity.ok("Token válido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }
    }
}
