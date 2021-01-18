package com.github.hugovallada.osworks.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.github.hugovallada.osworks.domain.model.Cliente;
import com.github.hugovallada.osworks.domain.repository.ClienteRepository;
import com.github.hugovallada.osworks.domain.service.CadastroClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private CadastroClienteService service;

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @GetMapping("/{nome}")
    public List<Cliente> findByNome(@PathVariable String nome) {
        System.out.println(nome);
        return repository.findByNomeContaining(nome);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        var cliente = repository.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok().body(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> adicionar(@RequestBody @Valid Cliente cliente) {
        var clienteCreated = service.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreated);

    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @RequestBody @Valid Cliente cliente) {
        if (!repository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteId);
        cliente = service.salvar(cliente);

        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
