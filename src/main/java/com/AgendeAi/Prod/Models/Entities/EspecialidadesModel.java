package com.AgendeAi.Prod.Models.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "especialidades")
@Data
public class EspecialidadesModel {

    @EmbeddedId
    private EspecialidadeId id;

    // Relacionamentos -------

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_profissional")
    @JoinColumn(name = "id_profissional")
    private ProfissionaisModel profissionaisModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_servico")
    @JoinColumn(name = "id_servico")
    private ServicosModel servicosModel;
}
