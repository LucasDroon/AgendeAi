package com.AgendeAi.Prod.Models.DTOs;

import com.AgendeAi.Prod.Models.Entities.AgendamentosModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Integer id,
        String nomeCliente,
        String nomeProfissional,
        String nomeServico,
        LocalDateTime dataHora,
        String status,
        BigDecimal valorPago

) {

    public AgendamentoResponseDTO toDTO(AgendamentosModel agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId_agendamento(),
                agendamento.getClientesModel().getNome(),
                agendamento.getProfissionaisModel() != null ? agendamento.getProfissionaisModel().getNome() : "Não definido",
                agendamento.getServicosModel().getNome(),
                agendamento.getData_hora(),
                agendamento.getStatus().name(),
                agendamento.getValor_pago()
        );
    }
}

