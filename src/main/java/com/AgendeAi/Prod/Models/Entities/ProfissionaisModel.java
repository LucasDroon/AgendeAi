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
@Table(name = ProfissionaisModel.TABLE_NAME)
public class ProfissionaisModel {

    public static final String TABLE_NAME = "profissionais";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profissional", unique = true)
    private Integer id_profissional;

    // Demais campos -------

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "percentual_comissao", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentual_comissao;

    @Column(name = "cor_agenda", length = 7)
    private String cor_agenda;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;
}
