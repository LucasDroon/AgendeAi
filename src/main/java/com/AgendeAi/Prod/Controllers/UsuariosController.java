package com.AgendeAi.Prod.Controllers;

import com.AgendeAi.Prod.Models.Entities.UsuariosModel;
import com.AgendeAi.Prod.Services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    // POST /usuarios - Cadastrar um novo usuário no sistema
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody UsuariosModel usuario) {
        UsuariosModel novoUsuario = usuariosService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // GET /usuarios - Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UsuariosModel>> listarTodos() {
        return ResponseEntity.ok(usuariosService.listarTodos());
    }
}