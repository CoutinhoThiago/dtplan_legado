package com.dtplan.domain.treino;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinoRepository extends JpaRepository<Treino, Long>{
    Page<Treino> findByUsuarioId(Long usuarioId, Pageable paginacao);
}
