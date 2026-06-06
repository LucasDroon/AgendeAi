package com.AgendeAi.Prod.Repositories;

import com.AgendeAi.Prod.Models.Entities.UsuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosModel, Integer> {
    Optional<UsuariosModel> findByEmail(String email);
}