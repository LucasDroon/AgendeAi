package com.AgendeAi.Prod.Models.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = VendaProdutosModel.TABLE_NAME)
public class VendaProdutosModel {

    public static final String TABLE_NAME = "vendas_produtos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venda", unique = true)
    private Integer id_venda;

    // Relacionamentos -------

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario")
    private UsuariosModel usuariosModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClientesModel clientesModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agendamento")
    private AgendamentosModel agendamentosModel;

    // Demais campos -------

    @Column(name = "cliente_avulso_nome", length = 100)
    private String cliente_avulso_nome;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime data_hora = LocalDateTime.now();

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private java.math.BigDecimal valor_total;

}
