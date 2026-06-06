package com.AgendeAi.Prod.Repositories;

import com.AgendeAi.Prod.Models.Entities.ProfissionaisModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfissionaisRepository extends JpaRepository<ProfissionaisModel, Integer> {

    // Retorna apenas os profissionais que estão ativos no sistema
    List<ProfissionaisModel> findByAtivoTrue();
}