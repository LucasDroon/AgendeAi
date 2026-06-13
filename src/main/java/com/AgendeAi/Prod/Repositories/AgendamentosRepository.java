package com.AgendeAi.Prod.Repositories;

import com.AgendeAi.Prod.Models.Entities.AgendamentosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface AgendamentosRepository extends JpaRepository<AgendamentosModel, Integer> {

    @EntityGraph(attributePaths = {"clientesModel", "profissionaisModel", "servicosModel", "usuariosModel"})
    List<AgendamentosModel> findAll();

    // Busca agendamentos de um profissional específico
    @Query("SELECT a FROM AgendamentosModel a WHERE a.profissionaisModel.id_profissional = :idProfissional")
    List<AgendamentosModel> buscarPorProfissional(@Param("idProfissional") Integer idProfissional);

    // Busca agendamentos dentro de um intervalo de tempo (útil para a agenda diária/semanal)
    @Query("SELECT a FROM AgendamentosModel a WHERE a.data_hora BETWEEN :inicio AND :fim")
    List<AgendamentosModel> buscarPorPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}