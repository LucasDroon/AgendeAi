package com.AgendeAi.Prod.Models.Entities;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = LogsModel.TABLE_NAME)
public class LogsModel {

    public static final String TABLE_NAME = "logs_sistema";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log", unique = true)
    private Integer id_log;

    // Relacionamentos -------

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_usuario")
    private UsuariosModel usuariosModel;

    // Demais campos -------

    @Column(name = "acao")
    private String acao;

    @Column(name = "data_hora")
    private LocalDateTime data_hora;

    @Column(name = "descricao")
    private String descricao;

}
