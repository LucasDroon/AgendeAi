package com.AgendeAi.Prod.Models.Entities;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import lombok.Data;

@Data
@Entity
@Table(name = UsuariosModel.TABLE_NAME)
public class UsuariosModel {

    public static final String TABLE_NAME = "usuarios";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", unique = true)
    private Integer id_usuario;

    // Relacionamentos -------

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_perfil")
    private PerfisModel perfisModel;

    // Demais colunas -------

    @Column(name = "nome")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "senha_hash")
    private String senha_hash;

    @Column(name = "status")
    private boolean status;
}
