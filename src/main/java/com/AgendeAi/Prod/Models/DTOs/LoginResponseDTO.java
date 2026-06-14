package com.AgendeAi.Prod.Models.DTOs;

public record LoginResponseDTO(String token, Integer id, String nome, String email, String role) {
}
