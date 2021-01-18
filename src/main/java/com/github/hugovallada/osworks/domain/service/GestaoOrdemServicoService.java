package com.github.hugovallada.osworks.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.github.hugovallada.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.github.hugovallada.osworks.domain.exception.NegocioException;
import com.github.hugovallada.osworks.domain.model.Cliente;
import com.github.hugovallada.osworks.domain.model.Comentario;
import com.github.hugovallada.osworks.domain.model.OrderServico;
import com.github.hugovallada.osworks.domain.model.StatusOrdemServico;
import com.github.hugovallada.osworks.domain.repository.ClienteRepository;
import com.github.hugovallada.osworks.domain.repository.ComentarioRepository;
import com.github.hugovallada.osworks.domain.repository.OrdemServicoRepository;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestaoOrdemServicoService {
    
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public OrderServico criar(OrderServico ordemServico) {

        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
            .orElseThrow(() -> new NegocioException("Cliente não encontrado"));


        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());
        ordemServico.setCliente(cliente);
        return ordemServicoRepository.save(ordemServico);
    }
    
    public List<OrderServico> buscarTodos() {
    	return ordemServicoRepository.findAll();
    }

    public OrderServico findById(Long id) {
        var ordemServico = buscar(id);

        return ordemServico;

    }

    public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
        var os = buscar(ordemServicoId);

        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(os);

        return comentarioRepository.save(comentario);
    }

    public void finalizar(Long ordemServicoId) {
        var os = buscar(ordemServicoId);
        os.finalizar();
        ordemServicoRepository.save(os);
    }

    private OrderServico buscar(Long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
    }


}
