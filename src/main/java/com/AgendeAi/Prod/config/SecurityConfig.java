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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import com.AgendeAi.Prod.config.SecurityFilter;

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
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/servicos/**").hasAnyRole("ADMIN", "PROFISSIONAL")
                        .requestMatchers(HttpMethod.GET, "/profissionais/**").hasAnyRole("ADMIN", "PROFISSIONAL")
                        .requestMatchers(HttpMethod.GET, "/clientes/**").hasAnyRole("ADMIN", "PROFISSIONAL")
                        .requestMatchers(HttpMethod.GET, "/agendamentos/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/agendamentos/**").hasAnyRole("ADMIN", "PROFISSIONAL")
                        .requestMatchers(HttpMethod.POST, "/clientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/clientes/**", "/agendamentos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/clientes/**", "/agendamentos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/clientes/**", "/agendamentos/**").hasRole("ADMIN")
                        
                        .requestMatchers(HttpMethod.POST, "/profissionais/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/profissionais/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/profissionais/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/profissionais/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/servicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/servicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/servicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/servicos/**").hasRole("ADMIN")

                        .requestMatchers("/dashboard", "/relatorios", "/caixa").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/agenda").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/totem/atendimentos").hasRole("PROFISSIONAL")
                        .requestMatchers(HttpMethod.GET, "/totem/servicos").hasAnyRole("ADMIN", "PROFISSIONAL")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public org.springframework.boot.web.servlet.FilterRegistrationBean<org.springframework.web.filter.CorsFilter> corsFilter() {
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173", "https://agende-ai-front.vercel.app"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);
        
        org.springframework.boot.web.servlet.FilterRegistrationBean<org.springframework.web.filter.CorsFilter> bean = 
            new org.springframework.boot.web.servlet.FilterRegistrationBean<>(new org.springframework.web.filter.CorsFilter(source));
        bean.setOrder(org.springframework.core.Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "https://agende-ai-front.vercel.app"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Custo 10 exigido nas regras de segurança
        return new BCryptPasswordEncoder(10);
    }
}