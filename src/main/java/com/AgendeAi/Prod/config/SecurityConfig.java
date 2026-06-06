package com.AgendeAi.Prod.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.AgendeAi.Prod.Config.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Autenticação Pública
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")

                        // Exclusivos do Admin
                        .requestMatchers("/clientes/**", "/profissionais/**", "/servicos/**", "/agendamentos/**").hasRole("ADMIN")
                        .requestMatchers("/dashboard", "/relatorios", "/caixa").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/agenda").hasRole("ADMIN")

                        // Exclusivos do Profissional
                        .requestMatchers(HttpMethod.POST, "/totem/atendimentos").hasRole("PROFISSIONAL")

                        // Leitura Pública Autenticada (Ambos acessam)
                        .requestMatchers(HttpMethod.GET, "/totem/servicos").hasAnyRole("ADMIN", "PROFISSIONAL")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Custo 10 exigido nas regras de segurança
        return new BCryptPasswordEncoder(10);
    }
}