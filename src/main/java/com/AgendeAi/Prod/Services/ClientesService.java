package com.AgendeAi.Prod.Services;

import com.AgendeAi.Prod.Models.Entities.ClientesModel;
import com.AgendeAi.Prod.Repositories.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    public List<ClientesModel> listarClientesAtivos() {
        return clientesRepository.findByAtivoTrue();
    }

    public Optional<ClientesModel> buscarPorId(Integer id) {
        return clientesRepository.findById(id);
    }

    @Transactional
    public ClientesModel salvarCliente(ClientesModel cliente) {
        // Validação: WhatsApp único exigido na documentação
        Optional<ClientesModel> clienteExistente = clientesRepository.findByTelefone(cliente.getTelefone());
        if (clienteExistente.isPresent() && !clienteExistente.get().getId_cliente().equals(cliente.getId_cliente())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este WhatsApp.");
        }
        return clientesRepository.save(cliente);
    }

    @Transactional
    public void inativarCliente(Integer id) {
        // Implementação da RN-C02: Inativação lógica apenas. Nunca DELETE físico.
        ClientesModel cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        // Aqui futuramente você pode adicionar a validação de "agendamentos futuros"
        // solicitada na documentação do endpoint PATCH /clientes/{id}/inativar

        cliente.setAtivo(false);
        clientesRepository.save(cliente);
    }
}