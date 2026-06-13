package com.AgendeAi.Prod.Controllers;

import com.AgendeAi.Prod.Models.Entities.ProfissionaisModel;
import com.AgendeAi.Prod.Services.ProfissionaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionaisController {

    @Autowired
    private ProfissionaisService profissionaisService;

    // GET /profissionais - Listar todos os profissionais ativos
    @GetMapping
    public ResponseEntity<List<ProfissionaisModel>> listarAtivos() {
        return ResponseEntity.ok(profissionaisService.listarProfissionaisAtivos());
    }

    // POST /profissionais - Cadastrar um novo profissional
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody ProfissionaisModel profissional) {
        try {
            ProfissionaisModel novoProfissional = profissionaisService.salvarProfissional(profissional);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProfissional);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    // PUT /profissionais/{id} - Atualizar dados cadastrais do profissional
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ProfissionaisModel profissionalAtualizado) {
        return profissionaisService.buscarPorId(id).map(profissionalExistente -> {
            profissionalExistente.setNome(profissionalAtualizado.getNome());
            profissionalExistente.setPercentual_comissao(profissionalAtualizado.getPercentual_comissao());
            profissionalExistente.setCor_agenda(profissionalAtualizado.getCor_agenda());

            try {
                ProfissionaisModel salvo = profissionaisService.salvarProfissional(profissionalExistente);
                return ResponseEntity.ok(salvo);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /profissionais/{id}/inativar - Alterar o status do profissional para inativo
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Integer id) {
        try {
            profissionaisService.inativarProfissional(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /profissionais/{id} - Inativar profissional (Exclusão lógica)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            profissionaisService.inativarProfissional(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}