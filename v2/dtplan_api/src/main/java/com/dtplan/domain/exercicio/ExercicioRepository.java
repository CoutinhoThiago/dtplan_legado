package com.dtplan.domain.exercicio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long>{
    Optional<Exercicio> findByNome(String nome);
}
