package com.dtplan.domain.alimento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    Optional<Object> findByNome(String frangoGrelhado);
}
