package com.AgendeAi.Prod.Services;

import com.AgendeAi.Prod.Models.Entities.UsuariosModel;
import com.AgendeAi.Prod.Repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuariosModel cadastrarUsuario(UsuariosModel usuario) {
        // Criptografa a senha em BCrypt antes de mandar para o Supabase
        usuario.setSenha_hash(passwordEncoder.encode(usuario.getSenha_hash()));
        return usuariosRepository.save(usuario);
    }
}