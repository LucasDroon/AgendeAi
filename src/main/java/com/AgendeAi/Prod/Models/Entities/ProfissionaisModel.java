package com.AgendeAi.Prod.Models.Entities;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Entity
@Table(name = ProfissionaisModel.TABLE_NAME)
public class ProfissionaisModel {

    public static final String TABLE_NAME = "profissionais";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profissionais", unique = true)
    private Integer id_profissionais;

    @Column(name = "nome")
    private String nome;

    @Column(name = "especialidades")
    private String especialidades;

    @Column(name = "percentual_comissao")
    private Double percentual_comissao;

    @Column(name = "cor_agenda")
    private String cor_agenda;
}
