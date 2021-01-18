package com.github.hugovallada.osworks.domain.repository;

import com.github.hugovallada.osworks.domain.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
