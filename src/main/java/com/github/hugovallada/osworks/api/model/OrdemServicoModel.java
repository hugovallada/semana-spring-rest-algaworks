package com.github.hugovallada.osworks.api.model;

import com.github.hugovallada.osworks.domain.model.Cliente;
import com.github.hugovallada.osworks.domain.model.StatusOrdemServico;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrdemServicoModel {

    private Long id;

    //private String nomeCliente;

    private ClienteResumoModel cliente;

    //private String emailCliente; padrão do mapper model nomedavariavel + Classe

    private String descricao;

    private BigDecimal preco;

    private StatusOrdemServico status;

    private OffsetDateTime dataAbertura;

    private OffsetDateTime dataFinalizacao;
}
