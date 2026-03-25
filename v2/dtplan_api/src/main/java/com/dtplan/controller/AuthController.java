package com.dtplan.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.dtplan.domain.usuario.Usuario;
import com.dtplan.domain.usuario.UsuarioRepository;
import com.dtplan.domain.usuario.dto.LoginDTO;
import com.dtplan.domain.usuario.dto.TokenDTO;
import com.dtplan.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody LoginDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        Usuario usuario = ((Usuario) authentication.getPrincipal());

        String tokenJWT = tokenService.criarToken(usuario);

        return ResponseEntity.ok(new TokenDTO(tokenJWT, "falta implementar", usuario.getId(), usuario.getNome()));
    }

    @PostMapping("/token")
    public ResponseEntity<?> validarToken(@RequestBody TokenDTO body) {
        String tokenJWT = body.token();

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
