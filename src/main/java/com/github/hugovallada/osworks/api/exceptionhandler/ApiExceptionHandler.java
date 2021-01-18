package com.github.hugovallada.osworks.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import com.github.hugovallada.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.github.hugovallada.osworks.domain.exception.NegocioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        var campos = new ArrayList<Problema.Campo>();
        
        for(var error : ex.getBindingResult().getAllErrors()) {
            var msg =  messageSource.getMessage(error, LocaleContextHolder.getLocale());
            var nome = ((FieldError) error).getField();
            
            campos.add(new Problema.Campo(nome, msg));
        }   
        
        var problema = new Problema();
        problema.setStatus(status.value());
        problema.setTitle("Um ou mais campos estão inválidos");
        problema.setDataHora(OffsetDateTime.now());
        problema.setCampos(campos);
        
        return super.handleExceptionInternal(ex, problema, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request){
        
        var problema = new Problema();
        problema.setStatus(HttpStatus.BAD_REQUEST.value());
        problema.setTitle(ex.getMessage());
        problema.setDataHora(OffsetDateTime.now());

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(NegocioException ex, WebRequest request){

        var problema = new Problema();
        problema.setStatus(HttpStatus.NOT_FOUND.value());
        problema.setTitle(ex.getMessage());
        problema.setDataHora(OffsetDateTime.now());

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }
    
}
