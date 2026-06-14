package com.AgendeAi.Prod.config;

import com.AgendeAi.Prod.Repositories.UsuariosRepository;
import com.AgendeAi.Prod.Services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Filtrando requisição: " + request.getMethod() + " " + request.getRequestURI());

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverToken(request);
        if (token != null) {
            try {
                var email = tokenService.validarToken(token);
                logger.info("Token validado para email: " + email);
                if (!email.isEmpty()) {
                    var usuario = usuariosRepository.findByEmail(email).orElse(null);

                    if (usuario != null && usuario.isAtivo()) {
                        var perfil = tokenService.extrairPerfil(token);
                        var authorityString = perfil.startsWith("ROLE_") ? perfil : "ROLE_" + perfil;
                        var authorities = Collections.singletonList(new SimpleGrantedAuthority(authorityString));
                        
                        logger.info("Autenticando usuário: " + usuario.getEmail() + " com autoridade: " + authorityString);

                        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        logger.warn("Usuário inexistente ou inativo: " + email);
                    }
                }
            } catch (Exception e) {
                logger.error("Erro ao processar autenticação no SecurityFilter", e);
            }
        } else {
            logger.warn("Token ausente na requisição: " + request.getMethod() + " " + request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}