package com.dtplan.domain.refeicao;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.refeicao.dto.ListarRefeicaoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByIdIn(List<Long> ids);

    List<ListarRefeicaoDTO> findDTOByDietaId(Long id);
    List<Refeicao> findByDietaId (Long id);

    //@Query("SELECT r FROM Refeicao r JOIN r.refeicaoAlimentos ra WHERE ra.alimento = :alimento")
    //List<Refeicao> findAllByAlimento(@Param("alimento") Alimento alimento);
}
