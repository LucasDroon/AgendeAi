package com.AgendeAi.Prod.Models.DTOs;

public record AgendamentoRequestDTO(
    String data_hora,
    Integer id_cliente,
    Integer id_profissional,
    Integer id_servico,
    Integer id_usuario
) {}
