package com.AgendeAi.Prod.Models.Entities;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = ItensVendaModel.TABLE_NAME)
public class ItensVendaModel {

    public static final String TABLE_NAME = "itens_venda";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item", unique = true)
    private Integer id_item;

    // Relacionamentos -------

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_venda")
    private VendaProdutosModel vendaProdutosModel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_produto")
    private ProdutosModel produtosModel;

    // Demais campos -------

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private java.math.BigDecimal preco_unitario;
}
