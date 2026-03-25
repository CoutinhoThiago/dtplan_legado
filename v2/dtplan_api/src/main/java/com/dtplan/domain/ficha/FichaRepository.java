package com.dtplan.domain.ficha;

import com.dtplan.domain.ficha.dto.ListarFichaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Long> {
    @Query("SELECT new com.dtplan.domain.ficha.dto.ListarFichaDTO(f) FROM Ficha f WHERE f.treino.id = :id")
    List<ListarFichaDTO> findByTreinoId(Long id);
}
