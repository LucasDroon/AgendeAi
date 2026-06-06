package com.AgendeAi.Prod.Repositories;

import com.AgendeAi.Prod.Models.Entities.ClientesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientesRepository extends JpaRepository<ClientesModel, Integer> {

    // Retorna apenas clientes com a flag ativo =2true
    List<ClientesModel> findByAtivoTrue();

    // Verifica se ja existe um cliente com esse telefone/whatsapp cadastrado
    Optional<ClientesModel> findByTelefone(String telefone);
}