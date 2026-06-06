package com.AgendeAi.Prod.Controllers;

import com.AgendeAi.Prod.Models.Entities.AgendamentosModel;
import com.AgendeAi.Prod.Models.Entities.StatusAgendamento;
import com.AgendeAi.Prod.Services.AgendamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentosController {

    @Autowired
    private AgendamentosService agendamentosService;

    // GET /agendamentos - Listar todos os agendamentos registrados
    @GetMapping
    public ResponseEntity<List<AgendamentosModel>> listarTodos() {
        return ResponseEntity.ok(agendamentosService.listarTodos());
    }

    // POST /agendamentos - Registrar um novo horário na agenda
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AgendamentosModel agendamento) {
        try {
            AgendamentosModel novoAgendamento = agendamentosService.criarAgendamento(agendamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    // PATCH /agendamentos/{id}/status - Atualizar dinamicamente o status do fluxo
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> mudarStatus(@PathVariable Integer id, @RequestParam StatusAgendamento status) {
        try {
            AgendamentosModel atualizado = agendamentosService.atualizarStatus(id, status);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}