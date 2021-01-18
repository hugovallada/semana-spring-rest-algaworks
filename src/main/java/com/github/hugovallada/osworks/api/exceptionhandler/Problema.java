package com.github.hugovallada.osworks.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Problema
{
    private Integer status;

    private OffsetDateTime dataHora;

    private String title;

    private List<Campo> campos;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Campo {
        private String nome; 
        private String mensagem;
    }
}
