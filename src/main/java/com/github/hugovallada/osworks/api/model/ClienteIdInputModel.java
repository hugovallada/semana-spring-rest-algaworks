package com.github.hugovallada.osworks.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClienteIdInputModel {

    @NotNull
    private Long id;
}
