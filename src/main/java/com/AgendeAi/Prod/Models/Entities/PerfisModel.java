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
@Table(name = PerfisModel.TABLE_NAME)
public class PerfisModel {

    public static final String TABLE_NAME = "perfis";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil", unique = true)
    private Integer id_perfil;

    @Column(name = "nome_perfil")
    private String nome_perfil;

}
