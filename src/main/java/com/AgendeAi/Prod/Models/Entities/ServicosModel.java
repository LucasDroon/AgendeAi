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
@Table(name = ServicosModel.TABLE_NAME)
public class ServicosModel {

    public static final String TABLE_NAME = "servicos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico", unique = true)
    private Integer id_servico;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "duracao_estimada")
    private Integer duracao_estimada;
}
