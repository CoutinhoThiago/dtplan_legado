package com.dtplan.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dtplan.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String criarToken (Usuario usuario) {
        try {
            Algorithm algoritimo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("login-auth-api") // Quem foi o emissor do token
                    .withSubject(usuario.getEmail()) // Quem é o usuario que está recebendo o token (salvat o login no token)
                    .withExpiresAt(dataExpiracao()) // Tempo de expiração
                    .sign(algoritimo); // Finalizar e criar token
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro de autenticação" + exception);
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
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(tokenJWT);
            return true;
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado" + exception);
            ///throw new TokenException.InvalidTokenException("Token inválido ou expirado");
        }
    }

    public String getSubject(String token) {
        try {
            if (token == null) {
                throw new RuntimeException("Token inválido ou expirado"); //throw new TokenException.NullTokenException("Token não fornecido");
            }
            Algorithm algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("login-auth-api") // Quem foi o emissor do token
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado" + exception); //throw new TokenException.InvalidTokenException("Token inválido");
        }
    }
}
