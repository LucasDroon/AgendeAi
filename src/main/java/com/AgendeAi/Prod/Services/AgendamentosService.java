package com.AgendeAi.Prod.Services;

import com.AgendeAi.Prod.Models.DTOs.AgendamentoRequestDTO;
import com.AgendeAi.Prod.Models.Entities.*;
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

    @Transactional(readOnly = true)
    public List<AgendamentosModel> listarTodos() {
        return agendamentosRepository.findAll();
    }

    public Optional<AgendamentosModel> buscarPorId(Integer id) {
        return agendamentosRepository.findById(id);
    }

    @Transactional
    public AgendamentosModel criarAgendamento(AgendamentoRequestDTO dto) {
        // Converter IDs para Entidades
        ClientesModel cliente = clientesRepository.findById(dto.id_cliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente informado não existe."));
        
        ServicosModel servico = servicosRepository.findById(dto.id_servico())
                .orElseThrow(() -> new IllegalArgumentException("Serviço informado não existe."));
        
        UsuariosModel usuario = usuariosRepository.findById(dto.id_usuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário do sistema informado não existe."));
        
        ProfissionaisModel profissional = null;
        if (dto.id_profissional() != null) {
            profissional = profissionaisRepository.findById(dto.id_profissional())
                    .orElseThrow(() -> new IllegalArgumentException("Profissional informado não existe."));
        }

        LocalDateTime dataHora = LocalDateTime.parse(dto.data_hora());
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data retroativa.");
        }

        AgendamentosModel agendamento = new AgendamentosModel();
        agendamento.setData_hora(dataHora);
        agendamento.setClientesModel(cliente);
        agendamento.setServicosModel(servico);
        agendamento.setUsuariosModel(usuario);
        agendamento.setProfissionaisModel(profissional);
        
        if ("PROFISSIONAL".equalsIgnoreCase(usuario.getPerfisModel().getNome_perfil())) {
            agendamento.setStatus(StatusAgendamento.Confirmado);
        } else {
            agendamento.setStatus(StatusAgendamento.Agendado);
        }
        
        agendamento.setValor_pago(servico.getPreco());

        if (profissional != null) {
            BigDecimal percentual = profissional.getPercentual_comissao();
            BigDecimal comissao = servico.getPreco().multiply(percentual).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            agendamento.setValor_comissao_gerada(comissao);
        } else {
            agendamento.setValor_comissao_gerada(BigDecimal.ZERO);
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

    @Transactional
    public void deletarAgendamento(Integer id) {
        if (!agendamentosRepository.existsById(id)) {
            throw new IllegalArgumentException("Agendamento não encontrado.");
        }
        agendamentosRepository.deleteById(id);
    }
}
