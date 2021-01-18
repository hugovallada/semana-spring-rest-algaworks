package com.github.hugovallada.osworks.domain.service;

import java.security.DrbgParameters.Reseed;

import javax.persistence.EntityExistsException;

import com.github.hugovallada.osworks.domain.exception.NegocioException;
import com.github.hugovallada.osworks.domain.model.Cliente;
import com.github.hugovallada.osworks.domain.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroClienteService {
    

    private ClienteRepository repository;

    @Autowired
    public CadastroClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente salvar(Cliente cliente) {
        var existe = repository.findByEmail(cliente.getEmail());

        if (existe != null && !existe.equals(cliente)) {
            throw new NegocioException("Já existe um cliente cadastrado com esse email");
        }

        return repository.save(cliente);
    }

    public void excluir(Long clienteId) {
        repository.deleteById(clienteId);
    }

}
