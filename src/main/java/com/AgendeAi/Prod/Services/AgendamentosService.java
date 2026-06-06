package com.AgendeAi.Prod.Services;

import com.AgendeAi.Prod.Models.Entities.AgendamentosModel;
import com.AgendeAi.Prod.Models.Entities.ProfissionaisModel;
import com.AgendeAi.Prod.Models.Entities.ServicosModel;
import com.AgendeAi.Prod.Models.Entities.StatusAgendamento;
import com.AgendeAi.Prod.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentosService {

    @Autowired
    private AgendamentosRepository agendamentosRepository;

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<AgendamentosModel> listarTodos() {
        return agendamentosRepository.findAll();
    }

    public Optional<AgendamentosModel> buscarPorId(Integer id) {
        return agendamentosRepository.findById(id);
    }

    @Transactional
    public AgendamentosModel criarAgendamento(AgendamentosModel agendamento) {
        // 1. Validação de data retroativa
        if (agendamento.getData_hora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível realizar um agendamento para uma data e hora passada.");
        }

        // 2. Validar se o cliente, serviço e usuário existem
        clientesRepository.findById(agendamento.getClientesModel().getId_cliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente informado não existe."));

        ServicosModel servico = servicosRepository.findById(agendamento.getServicosModel().getId_servico())
                .orElseThrow(() -> new IllegalArgumentException("Serviço informado não existe."));

        usuariosRepository.findById(agendamento.getUsuariosModel().getId_usuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário do sistema informado não existe."));

        // 3. Define o valor pago padrão baseado no preço atual do serviço (se não enviado)
        if (agendamento.getValor_pago() == null) {
            agendamento.setValor_pago(servico.getPreco());
        }

        // 4. Regra de Negócio de Comissão (Se houver profissional atribuído)
        if (agendamento.getProfissionaisModel() != null && agendamento.getProfissionaisModel().getId_profissional() != null) {
            ProfissionaisModel profissional = profissionaisRepository.findById(agendamento.getProfissionaisModel().getId_profissional())
                    .orElseThrow(() -> new IllegalArgumentException("Profissional informado não existe."));

            // valor_comissao = (preco_do_servico * percentual_comissao) / 100
            BigDecimal percentual = profissional.getPercentual_comissao();
            BigDecimal comissaoGerada = servico.getPreco()
                    .multiply(percentual)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            agendamento.setValor_comissao_gerada(comissaoGerada);
        } else {
            // Caso seja ordem de chegada / sem profissional definitivo mapeado ainda
            agendamento.setValor_comissao_gerada(BigDecimal.ZERO);
        }

        // 5. Todo agendamento novo nasce com o status 'Agendado'
        if (agendamento.getStatus() == null) {
            agendamento.setStatus(StatusAgendamento.Agendado);
        }

        return agendamentosRepository.save(agendamento);
    }

    @Transactional
    public AgendamentosModel atualizarStatus(Integer id, StatusAgendamento novoStatus) {
        AgendamentosModel agendamento = agendamentosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado."));

        agendamento.setStatus(novoStatus);
        return agendamentosRepository.save(agendamento);
    }
}