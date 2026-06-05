package com.AgendeAi.Prod.Models.Entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeId implements Serializable {
    private Integer id_profissional;
    private Integer id_servico;
}
