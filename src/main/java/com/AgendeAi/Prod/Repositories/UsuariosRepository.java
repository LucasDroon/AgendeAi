package com.AgendeAi.Prod.Repositories;

import com.AgendeAi.Prod.Models.Entities.UsuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosModel, Integer> {
    @Query("SELECT u FROM UsuariosModel u JOIN FETCH u.perfisModel WHERE u.email = :email")
    Optional<UsuariosModel> findByEmail(@Param("email") String email);
}