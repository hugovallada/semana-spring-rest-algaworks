package com.github.hugovallada.osworks.api.controller;

import com.github.hugovallada.osworks.api.model.ComentarioInputModel;
import com.github.hugovallada.osworks.api.model.ComentarioModel;
import com.github.hugovallada.osworks.domain.model.Comentario;
import com.github.hugovallada.osworks.domain.repository.OrdemServicoRepository;
import com.github.hugovallada.osworks.domain.service.GestaoOrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

    @Autowired
    private GestaoOrdemServicoService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ComentarioModel adicionar(@PathVariable Long ordemServicoId,
                                     @RequestBody @Valid ComentarioInputModel comentario) {

        var comment = service.adicionarComentario(ordemServicoId, comentario.getDescricao());
        return toModel(comment);
    }

    @GetMapping
    public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){

        var ordemServico = service.findById(ordemServicoId);

        return toCollectModels(ordemServico.getComentarios());

    }

    private ComentarioModel toModel(Comentario comentario) {
        return mapper.map(comentario, ComentarioModel.class);
    }

    private List<ComentarioModel> toCollectModels(List<Comentario> comentarios) {
        return comentarios.stream()
                .map((comentario) -> toModel(comentario))
                .collect(Collectors.toList());
    }
}
