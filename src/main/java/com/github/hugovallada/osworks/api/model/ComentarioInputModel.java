package com.github.hugovallada.osworks.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ComentarioInputModel {

    @NotBlank
    private String descricao;
}
