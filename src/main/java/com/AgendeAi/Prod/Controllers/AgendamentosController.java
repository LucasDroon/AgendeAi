package com.AgendeAi.Prod.Controllers;

import com.AgendeAi.Prod.Models.DTOs.AgendamentoRequestDTO;
import com.AgendeAi.Prod.Models.DTOs.AgendamentoResponseDTO;
import com.AgendeAi.Prod.Models.Entities.AgendamentosModel;
import com.AgendeAi.Prod.Models.Entities.StatusAgendamento;
import com.AgendeAi.Prod.Services.AgendamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentosController {

    @Autowired
    private AgendamentosService agendamentosService;

    // POST /agendamentos - Registrar um novo horário na agenda
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AgendamentoRequestDTO dto) {
        try {
            AgendamentosModel novoAgendamento = agendamentosService.criarAgendamento(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(novoAgendamento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    // PATCH /agendamentos/{id}/status - Atualizar dinamicamente o status do fluxo
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> mudarStatus(@PathVariable Integer id, @RequestParam StatusAgendamento status) {
        try {
            AgendamentosModel atualizado = agendamentosService.atualizarStatus(id, status);
            return ResponseEntity.ok(toDTO(atualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            agendamentosService.atualizarStatus(id, StatusAgendamento.Cancelado);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET /agendamentos - Listar todos os agendamentos registrados
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        List<AgendamentosModel> lista = agendamentosService.listarTodos();

        // Converte cada entidade para DTO antes de enviar ao frontend
        List<AgendamentoResponseDTO> dtos = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    private AgendamentoResponseDTO toDTO(AgendamentosModel a) {
        return new AgendamentoResponseDTO(
                a.getId_agendamento(),
                a.getClientesModel().getNome(),
                (a.getProfissionaisModel() != null) ? a.getProfissionaisModel().getNome() : "Sem profissional",
                a.getServicosModel().getNome(),
                a.getData_hora(),
                a.getStatus().name(),
                a.getValor_pago()
        );
    }
}