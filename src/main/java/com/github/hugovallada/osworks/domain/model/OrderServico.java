package com.github.hugovallada.osworks.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import com.github.hugovallada.osworks.domain.ValidationGroups;
import com.github.hugovallada.osworks.domain.exception.NegocioException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode.Include;

@Entity
@Table(name = "ordem_servico")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Long id;

    //@Valid // faz a validação de tudo no cliente
    //@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class) // define o q será validado baseado na interface
    @ManyToOne
    //@NotNull
    private Cliente cliente;

    //@NotEmpty
    private String descricao;

    //@NotNull
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
   // @JsonProperty(access = Access.READ_ONLY) // valores passados serão ignorados
    private StatusOrdemServico status;
    
    //@JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataAbertura;

    //@JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataFinalizacao;

    @OneToMany(mappedBy = "ordemServico")
    private List<Comentario> comentarios = new ArrayList<>();


    public void finalizar(){

        if(naoPodeSerFinalizada()){
            throw new NegocioException("Ordem de serviço não pode ser finalizada");
        }

        setStatus(StatusOrdemServico.FINALIZADA);
        setDataFinalizacao(OffsetDateTime.now());
    }

    private boolean podeSerFinalizada(){
        return StatusOrdemServico.ABERTA.equals(getStatus());
    }

    private  boolean naoPodeSerFinalizada(){
        return !podeSerFinalizada();
    }
}
