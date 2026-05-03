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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuariosModel usuariosModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClientesModel clientesModel;

    // Demais campos -------

    @Column(name = "data_hora")
    private LocalDateTime data_hora;

    @Column(name = "valor_total")
    private Double valor_total;

}
