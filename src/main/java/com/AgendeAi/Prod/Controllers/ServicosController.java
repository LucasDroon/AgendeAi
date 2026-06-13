package com.AgendeAi.Prod.Controllers;

import com.AgendeAi.Prod.Models.Entities.ServicosModel;
import com.AgendeAi.Prod.Services.ServicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicosController {

    @Autowired
    private ServicosService servicosService;

    // GET /servicos - Listar todos os serviços ativos
    @GetMapping
    public ResponseEntity<List<ServicosModel>> listarAtivos() {
        return ResponseEntity.ok(servicosService.listarServicosAtivos());
    }

    // POST /servicos - Cadastrar um novo serviço
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody ServicosModel servico) {
        try {
            ServicosModel novoServico = servicosService.salvarServico(servico);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    // PUT /servicos/{id} - Atualizar dados do serviço
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ServicosModel servicoAtualizado) {
        return servicosService.buscarPorId(id).map(servicoExistente -> {
            servicoExistente.setNome(servicoAtualizado.getNome());
            servicoExistente.setPreco(servicoAtualizado.getPreco());
            servicoExistente.setDuracao_estimada(servicoAtualizado.getDuracao_estimada());

            try {
                ServicosModel salvo = servicosService.salvarServico(servicoExistente);
                return ResponseEntity.ok(salvo);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /servicos/{id}/inativar - Alterar o status do serviço para inativo
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Integer id) {
        try {
            servicosService.inativarServico(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /servicos/{id} - Inativar serviço (Exclusão lógica)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            servicosService.inativarServico(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}