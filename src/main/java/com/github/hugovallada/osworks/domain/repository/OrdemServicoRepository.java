package com.github.hugovallada.osworks.domain.repository;

import com.github.hugovallada.osworks.domain.model.OrderServico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemServicoRepository extends JpaRepository<OrderServico, Long>{
    
}
