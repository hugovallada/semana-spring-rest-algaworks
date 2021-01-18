package com.github.hugovallada.osworks.domain.repository;

import java.util.List;

import com.github.hugovallada.osworks.domain.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNome(String nome);
    List<Cliente> findByNomeContaining(String nome); // Containing funciona como um like
    Cliente findByEmail(String email);
}
