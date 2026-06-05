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
@Table(name = ProdutosModel.TABLE_NAME)
public class ProdutosModel {

    public static final String TABLE_NAME = "produtos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto", unique = true)
    private Integer id_produto;

    // Demais campos -------

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao", nullable = false, columnDefinition = "text")
    private String descricao;

    @Column(name = "preco_venda")
    private BigDecimal preco_venda;
}
