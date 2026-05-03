package com.AgendeAi.Prod.Models.Entities;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = ItensVendaModel.TABLE_NAME)
public class ItensVendaModel {

    public static final String TABLE_NAME = "itens_vendas";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item", unique = true)
    private Integer id_item;

    // Relacionamentos -------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venda")
    private VendaProdutosModel vendaProdutosModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private ProdutosModel produtosModel;

    // Demais campos -------

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "preco_unitario")
    private Double preco_unitario;
}
