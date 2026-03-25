package com.dtplan.domain.usuario;

import com.dtplan.domain.treino.Treino;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.ClientInfoStatus;
import java.util.*;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Permissao permissao;

    private String nome;
    private String cpf;

    private String tipoUsuario;

    // Treinador
    private String cref;

    @ManyToMany
    @JoinTable(
            name = "treinador_alunos", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "treinador_id"), // Coluna do treinador
            inverseJoinColumns = @JoinColumn(name = "aluno_id") // Coluna do aluno
    )
    private List<Usuario> alunos;

    // Nutricionista
    private String crn;

    @ManyToMany
    @JoinTable(
            name = "nutricionista_pacientes", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "nutricionista_id"), // Coluna do nutricionista
            inverseJoinColumns = @JoinColumn(name = "paciente_id") // Coluna do paciente
    )
    private List<Usuario> pacientes;

    @Column(name = "data_nascimento")
    //private Date dataNascimento; // meses
    private String dataNascimento; // meses

    private int altura; // cm

    @Column(name = "peso_atual")
    private int pesoAtual; // kg

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_atividade")
    private NivelAtividade nivelAtividade;

    @Enumerated(EnumType.STRING)
    @Column(name = "objetivo")
    private Objetivo objetivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @OneToMany(mappedBy = "usuario")
    private List<Treino> treinos;

    public Usuario(
            String email,
            String senha,
            Permissao permissao,
            Genero genero,
            String nome, String cpf,
            String dataNascimento,
            int altura,
            int pesoAtual,
            String tipoUsuario,
            String cref,
            String crn
    ) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.permissao = permissao;
        this.genero = genero;
        this.tipoUsuario = tipoUsuario;
        this.altura = altura;
        this.pesoAtual = pesoAtual;
        this.dataNascimento = dataNascimento;

        this.cref = (cref == null || cref.isEmpty()) ? null : cref;
        this.crn = (crn == null || crn.isEmpty()) ? null : crn;

        this.alunos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.permissao == Permissao.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    public void atualizarDados(Optional<String> nome, Optional<String> cpf, Optional<String> dataNascimento, Optional<Integer> altura, Optional<Integer> pesoAtual, Optional<String> nivelAtividade, Optional<String> objetivo) {
        nome.ifPresent(n -> this.nome = n);
        cpf.ifPresent(c -> this.cpf = c);
        dataNascimento.ifPresent(d -> this.dataNascimento = d);
        altura.ifPresent(a -> this.altura = a);
        pesoAtual.ifPresent(p -> this.pesoAtual = p);
        nivelAtividade.ifPresent(n -> this.nivelAtividade = NivelAtividade.valueOf(n));
        objetivo.ifPresent(o -> this.objetivo = Objetivo.valueOf(o));
    }

    public void setAlunos(List<Usuario> listaDeAlunos) {
        this.alunos = listaDeAlunos;
    }
    public void setPacientes(List<Usuario> listaDePacientes) {
        this.pacientes = listaDePacientes;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}