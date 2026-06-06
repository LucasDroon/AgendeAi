package com.AgendeAi.Prod.Services;

import com.AgendeAi.Prod.Models.Entities.ServicosModel;
import com.AgendeAi.Prod.Repositories.ServicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicosService {

    @Autowired
    private ServicosRepository servicosRepository;

    public List<ServicosModel> listarServicosAtivos() {
        return servicosRepository.findByAtivoTrue();
    }

    public Optional<ServicosModel> buscarPorId(Integer id) {
        return servicosRepository.findById(id);
    }

    @Transactional
    public ServicosModel salvarServico(ServicosModel servico) {
        // Validação básica: Preço e Duração não podem ser negativos ou zero
        if (servico.getPreco() != null && servico.getPreco().doubleValue() < 0) {
            throw new IllegalArgumentException("O preço do serviço não pode ser negativo.");
        }
        if (servico.getDuracao_estimada() != null && servico.getDuracao_estimada() <= 0) {
            throw new IllegalArgumentException("A duração estimada do serviço deve ser maior que zero minutos.");
        }

        return servicosRepository.save(servico);
    }

    @Transactional
    public void inativarServico(Integer id) {
        // Exclusão lógica: Inativa o serviço para não aparecer mais no front,
        // mas mantém o histórico para as vendas e agendamentos antigos.
        ServicosModel servico = servicosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));

        servico.setAtivo(false);
        servicosRepository.save(servico);
    }
}