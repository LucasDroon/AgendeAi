package com.AgendeAi.Prod.Repositories;

import com.AgendeAi.Prod.Models.Entities.ServicosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicosRepository extends JpaRepository<ServicosModel, Integer> {

    // Retorna apenas os serviços com a flag ativo = true
    List<ServicosModel> findByAtivoTrue();
}