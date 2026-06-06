package com.AgendeAi.Prod.Services;

import com.AgendeAi.Prod.Models.Entities.ProfissionaisModel;
import com.AgendeAi.Prod.Repositories.ProfissionaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionaisService {

    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    public List<ProfissionaisModel> listarProfissionaisAtivos() {
        return profissionaisRepository.findByAtivoTrue();
    }

    public Optional<ProfissionaisModel> buscarPorId(Integer id) {
        return profissionaisRepository.findById(id);
    }

    @Transactional
    public ProfissionaisModel salvarProfissional(ProfissionaisModel profissional) {
        // Validação de Regra de Negócio: Comissão não pode ser negativa nem maior que 100%
        if (profissional.getPercentual_comissao() != null &&
                (profissional.getPercentual_comissao().doubleValue() < 0 ||
                        profissional.getPercentual_comissao().doubleValue() > 100)) {
            throw new IllegalArgumentException("O percentual de comissão deve estar entre 0.00 e 100.00.");
        }
        return profissionaisRepository.save(profissional);
    }

    @Transactional
    public void inativarProfissional(Integer id) {
        // Implementação de exclusão lógica para integridade dos agendamentos antigos
        ProfissionaisModel profissional = profissionaisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado."));

        profissional.setAtivo(false);
        profissionaisRepository.save(profissional);
    }
}