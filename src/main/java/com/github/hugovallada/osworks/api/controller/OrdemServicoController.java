package com.github.hugovallada.osworks.api.controller;

import com.github.hugovallada.osworks.api.model.OrdemServicoInputModel;
import com.github.hugovallada.osworks.api.model.OrdemServicoModel;
import com.github.hugovallada.osworks.domain.model.OrderServico;
import com.github.hugovallada.osworks.domain.service.GestaoOrdemServicoService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
    
    @Autowired
    private GestaoOrdemServicoService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoModel criar(@RequestBody @Valid OrdemServicoInputModel orderServico){
        var ordemServico = toEntity(orderServico);
        var servico = service.criar(ordemServico);

        return toModel(servico);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrdemServicoModel> listar() {

        var servicos =  service.buscarTodos();
        return toCollectionModel(servicos);
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId){
        var ordemServico = service.findById(ordemServicoId);

        if(ordemServico != null) {
            var model = toModel(ordemServico);

            return ResponseEntity.ok().body(model);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{ordemServicoId}/finalizacao")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long ordemServicoId){
        service.finalizar(ordemServicoId);
    }

    private OrdemServicoModel toModel(OrderServico servico) {
        return modelMapper.map(servico, OrdemServicoModel.class);
    }

    private List<OrdemServicoModel> toCollectionModel(List<OrderServico> ordens) {
        var ordensModel = ordens.stream()
                .map((ordem) -> toModel(ordem))
                .collect(Collectors.toList());

        return ordensModel;
    }

    private OrderServico toEntity(OrdemServicoInputModel os) {
        return modelMapper.map(os, OrderServico.class);
    }

}
