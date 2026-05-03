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

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = AgendamentosModel.TABLE_NAME)
public class AgendamentosModel {

    public static final String TABLE_NAME = "agendamentos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento", unique = true)
    private Integer id_agendamento;

    // Relacionamentos -------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClientesModel clientesModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissional")
    private ProfissionaisModel profissionaisModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico")
    private ServicosModel servicosModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuariosModel usuariosModel;

    // Demais campos --------

    @Column(name = "data_hora")
    private LocalDateTime data_hora;

    @Column(name = "status")
    private String status;

    @Column(name = "valor_pago")
    private Double valor_pago;

    @Column(name = "valor_comissao_gerada")
    private Double valor_comissao_gerada;

}
