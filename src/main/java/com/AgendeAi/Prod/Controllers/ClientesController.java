package com.AgendeAi.Prod.Controllers;

import com.AgendeAi.Prod.Models.Entities.ClientesModel;
import com.AgendeAi.Prod.Services.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    // GET /clientes - Listar clientes ativos
    @GetMapping
    public ResponseEntity<List<ClientesModel>> listarAtivos() {
        return ResponseEntity.ok(clientesService.listarClientesAtivos());
    }

    // POST /clientes - Cadastrar novo cliente
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody ClientesModel cliente) {
        try {
            ClientesModel novoCliente = clientesService.salvarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    // PUT /clientes/{id} - Atualizar dados do cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ClientesModel clienteAtualizado) {
        return clientesService.buscarPorId(id).map(clienteExistente -> {
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setTelefone(clienteAtualizado.getTelefone());
            clienteExistente.setData_nascimento(clienteAtualizado.getData_nascimento());

            try {
                ClientesModel salvo = clientesService.salvarCliente(clienteExistente);
                return ResponseEntity.ok(salvo);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /clientes/{id}/inativar - Inativar cliente
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Integer id) {
        try {
            clientesService.inativarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /clientes/{id} - Inativar cliente (Exclusão lógica)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            clientesService.inativarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}