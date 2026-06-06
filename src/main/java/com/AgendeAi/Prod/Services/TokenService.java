package com.AgendeAi.Prod.Services;

import com.AgendeAi.Prod.Models.Entities.UsuariosModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret:my-secret-key-agendeai}")
    private String secret;

    public String gerarToken(UsuariosModel usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String perfil = usuario.getPerfisModel().getNome_perfil().toUpperCase();

            // Regra RN: Expiracao de 12h para ADMIN e 8h para PROFISSIONAL
            int horasExpiracao = perfil.equals("ADMIN") ? 12 : 8;

            return JWT.create()
                    .withIssuer("AgendeAi")
                    .withSubject(usuario.getEmail())
                    .withClaim("perfil", perfil)
                    .withExpiresAt(LocalDateTime.now().plusHours(horasExpiracao).toInstant(ZoneOffset.of("-04:00")))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("AgendeAi")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public String extrairPerfil(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("AgendeAi")
                    .build()
                    .verify(token)
                    .getClaim("perfil").asString();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }
}