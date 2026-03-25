package com.dtplan.domain.execucao;

import com.dtplan.domain.execucao.dto.ListarExecucaoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecucaoRepository extends JpaRepository<Execucao, Long> {
    @Query("SELECT new com.dtplan.domain.execucao.dto.ListarExecucaoDTO(e) " +
            "FROM Execucao e WHERE e.ficha.id = :fichaId")
    List<ListarExecucaoDTO> findDtoByFichaId(@Param("fichaId") Long fichaId);

    List<Execucao> findByFichaId(Long fichaId);
    List<Execucao> findByTreinoId(Long treinoId);
}