package com.AgendeAi.Prod.Models.Entities;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Entity
@Table(name = ServicosModel.TABLE_NAME)
public class ServicosModel {

    public static final String TABLE_NAME = "servicos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico", unique = true)
    private Integer id_servico;

    // Demais campos -------

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "duracao_estimada", nullable = false)
    private Integer duracao_estimada;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;
}
