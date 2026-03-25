package com.dtplan.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String login);
    Usuario findByNome(String nome);
    Usuario findByCpf(String cpf);

    @Query("SELECT u FROM Usuario u WHERE u.id IN :ids")
    List<Usuario> findAlunosById(@Param("ids") List<Long> ids);

    @Query("SELECT u FROM Usuario u WHERE u.id IN :ids")
    List<Usuario> findPacientesById(@Param("ids") List<Long> ids);
}
