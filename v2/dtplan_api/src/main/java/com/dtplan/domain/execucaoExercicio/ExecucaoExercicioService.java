package com.dtplan.domain.execucaoExercicio;

import com.dtplan.domain.execucaoExercicio.dto.CadastrarExecucaoExercicioDTO;
import com.dtplan.domain.execucaoExercicio.dto.DetalharExecucaoExercicioDTO;
import com.dtplan.domain.execucaoExercicio.dto.EditarExecucaoExercicioDTO;
import com.dtplan.domain.execucaoExercicio.dto.ListarExecucaoExercicioDTO;
import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import com.dtplan.domain.exercicio.dto.DetalharExercicioDTO;
import com.dtplan.domain.exercicio.dto.EditarExercicioDTO;
import com.dtplan.domain.exercicio.dto.ListarExerciciosDTO;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecucaoExercicioService {

    @Autowired
    private ExecucaoExercicioRepository execucaoExercicioReposiory;

    @Transactional
    public DetalharExecucaoExercicioDTO cadastrar(CadastrarExecucaoExercicioDTO dados) {
        ExecucaoExercicio novaExecucaoExercicio = new ExecucaoExercicio(dados);
        execucaoExercicioReposiory.save(novaExecucaoExercicio);
        return new DetalharExecucaoExercicioDTO(novaExecucaoExercicio);
    }

    @Transactional
    public DetalharExecucaoExercicioDTO atualizar(long id, EditarExecucaoExercicioDTO dados) {
        ExecucaoExercicio execucaoExercicio = execucaoExercicioReposiory.getReferenceById(id);
        //execucaoExercicio.atualizarInformacoes(dados);
        System.out.println("FATA IMPLEMENTAR A ATUALIZAÇÃO DO FICHA EXERCICIO");
        return new DetalharExecucaoExercicioDTO(execucaoExercicio);
    }

    @Transactional
    public void excluir(long id) {
        execucaoExercicioReposiory.deleteById(id);
    }

    public List<ExecucaoExercicio> buscarExerciciosPorIds(List<Long> ids) {
        return execucaoExercicioReposiory.findAllById(ids);
    }

    public Page<ListarExecucaoExercicioDTO> listar(Pageable paginacao) {
        //return execucaoExercicioReposiory.findAll(paginacao).map(ListarExerciciosDTO::new);
        //return new ListarExecucaoExercicioDTO();
        return null;
    }

    public DetalharExecucaoExercicioDTO detalhar(long id) {
        ExecucaoExercicio execucaoExercicio = execucaoExercicioReposiory.getReferenceById(id);
        return null;
    }
}
