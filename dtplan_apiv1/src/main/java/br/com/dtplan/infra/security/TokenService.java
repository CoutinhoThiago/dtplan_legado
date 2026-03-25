package br.com.dtplan.infra.security;

import br.com.dtplan.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}*")
    private String secret;

    public String gerarToken(Usuario usuario) {
        //System.out.println(secret);
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API dtplan")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerrar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API dtplan")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        // Obtenha o momento atual em um fuso horário específico
        LocalDateTime agora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

        // Adiciona 24 horas ao momento atual
        LocalDateTime dataExpiracao = agora.plusHours(2);

        // Converte LocalDateTime para Instant considerando o mesmo fuso horário
        return dataExpiracao.toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean isTokenValido(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            JWT.require(algoritmo)
                    .withIssuer("API dtplan")
                    .build()
                    .verify(tokenJWT);
            return true; // Token válido
        } catch (JWTVerificationException exception) {
            return false; // Token inválido ou expirado
        }
    }
}
