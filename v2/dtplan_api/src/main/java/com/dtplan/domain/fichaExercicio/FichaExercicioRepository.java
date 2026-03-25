package com.dtplan.domain.fichaExercicio;

import com.dtplan.domain.fichaExercicio.dto.FichaExercicioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichaExercicioRepository extends JpaRepository<FichaExercicio, Long> {
    void deleteByFichaId(Long id);
    void deleteById(Long id);
    //List<FichaExercicioDTO> findByFichaId(Long id);
    int countByFichaId(Long fichaId);
}