package com.dtplan.domain.refeicaoAlimento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefeicaoAlimentoRepository extends JpaRepository<RefeicaoAlimento, Long> {
    void deleteByRefeicaoId(Long id);

    List<RefeicaoAlimento> findByRefeicaoId(Long id);
}