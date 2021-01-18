package com.github.hugovallada.osworks.api.model;

import com.github.hugovallada.osworks.domain.model.Cliente;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class OrdemServicoInputModel {

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    @Valid
    @NotNull
    private ClienteIdInputModel cliente;


}
