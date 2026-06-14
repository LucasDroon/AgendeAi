package com.AgendeAi.Prod.Controllers;

import com.AgendeAi.Prod.Models.DTOs.LoginRequestDTO;
import com.AgendeAi.Prod.Models.DTOs.LoginResponseDTO;
import com.AgendeAi.Prod.Models.Entities.UsuariosModel;
import com.AgendeAi.Prod.Repositories.UsuariosRepository;
import com.AgendeAi.Prod.Services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login") // Endpoint de autenticação
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO data) {
        Optional<UsuariosModel> usuarioOpt = usuariosRepository.findByEmail(data.email());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário não encontrado.");
        }

        UsuariosModel usuario = usuarioOpt.get();

        if (!usuario.isAtivo()) {
            return ResponseEntity.status(403).body("Usuário inativo.");
        }

        // Bate a senha vinda do JSON com o Hash no Supabase
        if (passwordEncoder.matches(data.senha(), usuario.getSenha_hash())) {
            String token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(new LoginResponseDTO(
                token, 
                usuario.getId_usuario(), 
                usuario.getNome(), 
                usuario.getEmail(),
                usuario.getPerfisModel().getNome_perfil()
            ));
        }

        return ResponseEntity.status(401).body("Credenciais inválidas.");
    }
}