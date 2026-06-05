package com.AgendeAi.Prod.Models.Entities;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = ClientesModel.TABLE_NAME)
public class ClientesModel {

    public static final String TABLE_NAME = "clientes";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", unique = true)
    private Integer id_cliente;

    // Demais campos -------

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "telefone", nullable = false, unique = true, length = 15)
    private String telefone;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate data_nascimento;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime data_cadastro = LocalDateTime.now();

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;



}
