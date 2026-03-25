package com.dtplan.infra.config;

import com.dtplan.domain.alimento.Alimento;
import com.dtplan.domain.alimento.AlimentoRepository;
import com.dtplan.domain.alimento.AlimentoService;
import com.dtplan.domain.dieta.Dieta;
import com.dtplan.domain.dieta.DietaRepository;
import com.dtplan.domain.dieta.DietaService;
import com.dtplan.domain.dieta.TipoDieta;
import com.dtplan.domain.dieta.dto.CadastrarDietaDTO;
import com.dtplan.domain.exercicio.Exercicio;
import com.dtplan.domain.exercicio.ExercicioRepository;
import com.dtplan.domain.exercicio.ExercicioService;
import com.dtplan.domain.exercicio.dto.CadastrarExercicioDTO;
import com.dtplan.domain.ficha.FichaRepository;
import com.dtplan.domain.ficha.FichaService;
import com.dtplan.domain.ficha.dto.CadastrarFichaDTO;
import com.dtplan.domain.fichaExercicio.FichaExercicio;
import com.dtplan.domain.fichaExercicio.FichaExercicioRepository;
import com.dtplan.domain.fichaExercicio.FichaExercicioService;
import com.dtplan.domain.fichaExercicio.dto.CadastrarFichaExercicioDTO;
import com.dtplan.domain.refeicao.Refeicao;
import com.dtplan.domain.refeicao.RefeicaoRepository;
import com.dtplan.domain.refeicao.RefeicaoService;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimento;
import com.dtplan.domain.refeicaoAlimento.RefeicaoAlimentoRepository;
import com.dtplan.domain.treino.TreinoRepository;
import com.dtplan.domain.treino.TreinoService;
import com.dtplan.domain.treino.dto.CadastroTreinoDTO;
import com.dtplan.domain.usuario.*;
import com.dtplan.domain.usuario.dto.CadastrarUsuarioDTO;
import com.dtplan.domain.usuario.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ExercicioRepository exercicioRepository;
    private final TreinoRepository treinoRepository;
    private final FichaRepository fichaRepository;
    private final DietaRepository dietaRepository;
    private final AlimentoRepository alimentoRepository;
    private final RefeicaoRepository refeicaoRepository;
    private final FichaExercicioRepository fichaExercicioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ExercicioService exercicioService;

    @Autowired
    private TreinoService treinoService;

    @Autowired
    private FichaService fichaService;

    @Autowired
    private DietaService dietaService;

    @Autowired
    private AlimentoService alimentoService;

    @Autowired
    private RefeicaoService refeicaoService;

    @Autowired
    private FichaExercicioService fichaExercicioService;

    @  Autowired
    RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    public DataInitializer(UsuarioRepository usuarioRepository, ExercicioRepository exercicioRepository, TreinoRepository treinoRepository, FichaRepository fichaRepository, DietaRepository dietaRepository, AlimentoRepository alimentoRepository, RefeicaoRepository refeicaoRepository, FichaExercicioRepository fichaExercicioRepository, RefeicaoAlimentoRepository refeicaoAlimentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.exercicioRepository = exercicioRepository;
        this.treinoRepository = treinoRepository;
        this.fichaRepository = fichaRepository;
        this.dietaRepository = dietaRepository;
        this.alimentoRepository = alimentoRepository;
        this.refeicaoRepository = refeicaoRepository;
        this.fichaExercicioRepository = fichaExercicioRepository;
        this.refeicaoAlimentoRepository = refeicaoAlimentoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe algum usuário no banco de dados
        if (usuarioRepository.count() == 0) {
            CadastrarUsuarioDTO admin = new CadastrarUsuarioDTO(
                    "tcoutinhossilva@gmail.com",
                    "12345678",
                    Permissao.ADMIN,
                    Genero.MASCULINO,
                    "admin",
                    "08866966584",
                    "01-01-2000",
                    172,
                    75,
                    "ambos",
                    "12345",
                    "12345"
            );

            usuarioService.cadastrarUsuario(admin);
            System.out.println("Usuário admin criado com sucesso!");

            CadastrarUsuarioDTO aluno = new CadastrarUsuarioDTO(
                    "aluno@gmail.com",
                    "12345678",
                    Permissao.ADMIN,
                    Genero.OUTRO,
                    "aluno",
                    "00000000001",
                    "01-01-2000",
                    190,
                    80,
                    "normal",
                    "",
                    ""
            );

            usuarioService.cadastrarUsuario(aluno);
            System.out.println("Usuário aluno criado com sucesso!");

            CadastrarUsuarioDTO paciente = new CadastrarUsuarioDTO(
                    "paciente@gmail.com",
                    "12345678",
                    Permissao.ADMIN,
                    Genero.FEMININO,
                    "paciente",
                    "00000000002",
                    "01-01-2000",
                    160,
                    55,
                    "normal",
                    "",
                    ""
            );

            usuarioService.cadastrarUsuario(paciente);
            System.out.println("Usuário paciente criado com sucesso!");

            Usuario adminUsuario = (Usuario) usuarioRepository.findByEmail(admin.email());
            Usuario alunoUsuario = (Usuario) usuarioRepository.findByEmail(aluno.email());
            Usuario pacienteUsuario = (Usuario) usuarioRepository.findByEmail(paciente.email());

            List<Long> alunosIds = List.of(2L, 3L);
            List<Usuario> listaDeAlunos = usuarioRepository.findAlunosById(alunosIds);
            System.out.println(listaDeAlunos);
            adminUsuario.setAlunos(listaDeAlunos);

            List<Long> pacientesIds = List.of(3L);
            List<Usuario> listaDePacientes = usuarioRepository.findPacientesById(pacientesIds);
            System.out.println(listaDePacientes);
            adminUsuario.setPacientes(listaDePacientes);

            // Salvar as alterações no banco de dados
            usuarioRepository.save(adminUsuario);
        }

        // Cadastrando exercícios se não houver nenhum no banco
        if (exercicioRepository.count() == 0) {
            List<CadastrarExercicioDTO> exercicios = List.of(
                    // Exercícios para o treino A - Push (Peito e Ombros)
                    // ----------------------------------------------------------
                    new CadastrarExercicioDTO("Supino reto", true, 1, "Exercício para peito", "Peito"),
                    new CadastrarExercicioDTO("Crucifixo com halteres", true, 1, "Exercício para peito", "Peito"),
                    new CadastrarExercicioDTO("Supino inclinado", true, 1, "Exercício para peito", "Peito"),
                    new CadastrarExercicioDTO("Supino declinado", true, 1, "Exercício para peito", "Peito"),
                    new CadastrarExercicioDTO("Tríceps coice com halter", true, 1, "Exercício para tríceps", "Braços"),
                    new CadastrarExercicioDTO("Tríceps pulley corda", true, 1, "Exercício para tríceps", "Braços"),
                    new CadastrarExercicioDTO("Elevação lateral com halteres", true, 1, "Exercício para ombros", "Ombros"),
                    new CadastrarExercicioDTO("Desenvolvimento com halteres (pegada neutra)", true, 1, "Exercício para ombros", "Ombros"),

                    // Exercícios para o treino B - Pull (Costas e Bíceps)
                    // ----------------------------------------------------------
                    new CadastrarExercicioDTO("Puxador frontal pegada pronada", true, 1, "Exercício para costas", "Costas"),
                    new CadastrarExercicioDTO("Puxador frontal unilateral", true, 1, "Exercício para costas", "Costas"),
                    new CadastrarExercicioDTO("Remada cavalinho (pegada neutra)", true, 1, "Exercício para costas", "Costas"),
                    new CadastrarExercicioDTO("Remada cavalinho (pegada pronada)", true, 1, "Exercício para costas", "Costas"),
                    new CadastrarExercicioDTO("Rosca direta com barra W", true, 1, "Exercício para bíceps", "Braços"),
                    new CadastrarExercicioDTO("Rosca scott com barra W", true, 1, "Exercício para bíceps", "Braços"),
                    new CadastrarExercicioDTO("Rosca inversa com barra W", true, 1, "Exercício para bíceps", "Braços"),
                    new CadastrarExercicioDTO("Barra Fixa", true, 1, "Exercício para costas", "Costas"),
                    new CadastrarExercicioDTO("Face Pull", true, 1, "Exercício para ombros e costas", "Ombros"),
                    new CadastrarExercicioDTO("Pulldown", true, 1, "Exercício para costas", "Costas"),

                    // Exercícios para o treino C - Legs (Pernas)
                    // ----------------------------------------------------------
                    new CadastrarExercicioDTO("Agachamento búlgaro", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Agachamento smith", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Agachamento sumô no smith", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Cadeira extensora", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Cadeira abdutora", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Mesa flexora", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Stiff", true, 1, "Exercício para pernas e costas", "Pernas"),
                    new CadastrarExercicioDTO("Panturrilha sentado máquina", true, 1, "Exercício para panturrilhas", "Pernas"),
                    new CadastrarExercicioDTO("Leg Press", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Agachamento Frontal", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Leg Curl", true, 1, "Exercício para pernas", "Pernas"),
                    new CadastrarExercicioDTO("Avanço", true, 1, "Exercício para pernas", "Pernas"),

                    // Exercícios HIIT
                    // ----------------------------------------------------------
                    new CadastrarExercicioDTO("HIIT", true, 1, "Exercício para resistência", "HIIT"),

                    // Exercícios Abdominais
                    // ----------------------------------------------------------
                    new CadastrarExercicioDTO("Abdominais", true, 1, "Exercício para abdômen", "Abdômen")
            );

            // Cadastra cada exercício no banco de dados
            exercicios.forEach(exercicioService::cadastrarExercicio);
            System.out.println("Exercícios cadastrados com sucesso!");

            FichaExercicio teste = new FichaExercicio(fichaRepository.getReferenceById(1L), exercicioRepository.getReferenceById(1L), 2, 3, 20, 0, 0, 0);
        }

        // Cadastrando treinos se não houver nenhum no banco
        if (treinoRepository.count() == 0) {
            List<CadastroTreinoDTO> treinos = List.of(
                    new CadastroTreinoDTO(
                            "Treino ABC",
                            "",
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null), // UsuarioDTO com login
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null) // UsuarioDTO com login
                    ),
                    new CadastroTreinoDTO(
                            "Rotina de Cárdio",
                            "",
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null), // UsuarioDTO com login
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null) // UsuarioDTO com login
                    ),
                    new CadastroTreinoDTO(
                            "Rotina de Abdominal",
                            "",
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null), // UsuarioDTO com login
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null) // UsuarioDTO com login
                    ),
                    new CadastroTreinoDTO(
                            "Treino sem ficha (teste)",
                            "",
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null), // UsuarioDTO com login
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null) // UsuarioDTO com login
                    ),

                    new CadastroTreinoDTO(
                            "Treino ABC aluno",
                            "",
                            new UsuarioDTO(null, null, "tcoutinhossilva@gmail.com", null), // UsuarioDTO com login
                            new UsuarioDTO(null, null, "aluno@gmail.com", null) // UsuarioDTO com login
                    ),
                    new CadastroTreinoDTO(
                            "Treino teste aluno (teste)",
                            "",
                            new UsuarioDTO(null, null, "aluno@gmail.com", null), // UsuarioDTO com login
                            new UsuarioDTO(null, null, "aluno@gmail.com", null) // UsuarioDTO com login
                    )


            );

            // Cadastra cada treino no banco de dados
            treinos.forEach(treinoService::cadastrarTreino);
            System.out.println("Treinos cadastrados com sucesso!");
        }

        // Agora você pode adicionar as fichas para o treino
        if (fichaRepository.count() == 0) {
            List<CadastrarFichaDTO> fichas = List.of(
                    // Fichas para o treino ABC
                    new CadastrarFichaDTO("Ficha A - Push", 1L, new ArrayList<>()),
                    new CadastrarFichaDTO("Ficha B - Pull", 1L, new ArrayList<>()),
                    new CadastrarFichaDTO("Ficha C - Legs", 1L, new ArrayList<>()),
                    new CadastrarFichaDTO("Ficha HIIT", 2L, new ArrayList<>()),
                    new CadastrarFichaDTO("Ficha Abdominal", 3L, new ArrayList<>()),

                    //Aluno
                    new CadastrarFichaDTO("Ficha A - Push", 5L, new ArrayList<>()),
                    new CadastrarFichaDTO("Ficha B - Pull", 5L, new ArrayList<>()),
                    new CadastrarFichaDTO("Ficha C - Legs", 5L, new ArrayList<>())
            );

            // Cadastra cada ficha no banco de dados
            fichas.forEach(fichaService::cadastrarFicha);
            System.out.println("Fichas cadastradas com sucesso!");

            List<CadastrarFichaExercicioDTO> fichaExercicios = List.of(
                    // Ficha A - Push (Peito e Ombros)
                    new CadastrarFichaExercicioDTO(1L, 1L, 2, 3, 20, 0, 0), // Supino reto
                    new CadastrarFichaExercicioDTO(1L, 2L, 2, 3, 20, 0, 0), // Crucifixo com halteres
                    new CadastrarFichaExercicioDTO(1L, 3L, 2, 3, 20, 0, 0), // Supino inclinado
                    new CadastrarFichaExercicioDTO(1L, 4L, 2, 3, 20, 0, 0), // Supino declinado
                    new CadastrarFichaExercicioDTO(1L, 5L, 2, 3, 20, 0, 0), // Tríceps coice com halter
                    new CadastrarFichaExercicioDTO(1L, 6L, 2, 3, 20, 0, 0), // Tríceps pulley corda
                    new CadastrarFichaExercicioDTO(1L, 7L, 2, 3, 20, 0, 0), // Elevação lateral com halteres
                    new CadastrarFichaExercicioDTO(1L, 8L, 2, 3, 20, 0, 0), // Desenvolvimento com halteres (pegada neutra)

                    // Ficha B - Pull (Costas e Bíceps)
                    new CadastrarFichaExercicioDTO(2L, 9L, 2, 3, 20, 0, 0), // Puxador frontal pegada pronada
                    new CadastrarFichaExercicioDTO(2L, 10L, 2, 3, 20, 0, 0), // Puxador frontal unilateral
                    new CadastrarFichaExercicioDTO(2L, 11L, 2, 3, 20, 0, 0), // Remada cavalinho (pegada neutra)
                    new CadastrarFichaExercicioDTO(2L, 12L, 2, 3, 20, 0, 0), // Remada cavalinho (pegada pronada)
                    new CadastrarFichaExercicioDTO(2L, 13L, 2, 3, 20, 0, 0), // Rosca direta com barra W
                    new CadastrarFichaExercicioDTO(2L, 14L, 2, 3, 20, 0, 0), // Rosca scott com barra W
                    new CadastrarFichaExercicioDTO(2L, 15L, 2, 3, 20, 0, 0), // Rosca inversa com barra W
                    new CadastrarFichaExercicioDTO(2L, 16L, 2, 3, 20, 0, 0), // Barra Fixa
                    new CadastrarFichaExercicioDTO(2L, 17L, 2, 3, 20, 0, 0), // Face Pull
                    new CadastrarFichaExercicioDTO(2L, 18L, 2, 3, 20, 0, 0), // Pulldown

                    // Ficha C - Legs (Pernas)
                    new CadastrarFichaExercicioDTO(3L, 19L, 2, 3, 20, 0, 0), // Agachamento búlgaro
                    new CadastrarFichaExercicioDTO(3L, 20L, 2, 3, 20, 0, 0), // Agachamento smith
                    new CadastrarFichaExercicioDTO(3L, 21L, 2, 3, 20, 0, 0), // Agachamento sumô no smith
                    new CadastrarFichaExercicioDTO(3L, 22L, 2, 3, 20, 0, 0), // Cadeira extensora
                    new CadastrarFichaExercicioDTO(3L, 23L, 2, 3, 20, 0, 0), // Cadeira abdutora
                    new CadastrarFichaExercicioDTO(3L, 24L, 2, 3, 20, 0, 0), // Mesa flexora
                    new CadastrarFichaExercicioDTO(3L, 25L, 2, 3, 20, 0, 0), // Stiff
                    new CadastrarFichaExercicioDTO(3L, 26L, 2, 3, 20, 0, 0), // Panturrilha sentado máquina
                    new CadastrarFichaExercicioDTO(3L, 27L, 2, 3, 20, 0, 0), // Leg Press
                    new CadastrarFichaExercicioDTO(3L, 28L, 2, 3, 20, 0, 0), // Agachamento Frontal
                    new CadastrarFichaExercicioDTO(3L, 29L, 2, 3, 20, 0, 0), // Leg Curl
                    new CadastrarFichaExercicioDTO(3L, 30L, 2, 3, 20, 0, 0), // Avanço

                    // Ficha HIIT
                    new CadastrarFichaExercicioDTO(4L, 31L, 2, 3, 20, 0, 0), // HIIT

                    // Ficha Abdominal
                    new CadastrarFichaExercicioDTO(5L, 32L, 2, 3, 20, 0, 0),  // Abdominais

                    // Ficha A - Push (Peito e Ombros)
                    new CadastrarFichaExercicioDTO(6L, 1L, 2, 3, 20, 0, 0), // Supino reto
                    new CadastrarFichaExercicioDTO(6L, 2L, 2, 3, 20, 0, 0), // Crucifixo com halteres
                    new CadastrarFichaExercicioDTO(6L, 3L, 2, 3, 20, 0, 0), // Supino inclinado
                    new CadastrarFichaExercicioDTO(6L, 4L, 2, 3, 20, 0, 0), // Supino declinado
                    new CadastrarFichaExercicioDTO(6L, 5L, 2, 3, 20, 0, 0), // Tríceps coice com halter
                    new CadastrarFichaExercicioDTO(6L, 6L, 2, 3, 20, 0, 0), // Tríceps pulley corda
                    new CadastrarFichaExercicioDTO(6L, 7L, 2, 3, 20, 0, 0), // Elevação lateral com halteres
                    new CadastrarFichaExercicioDTO(6L, 8L, 2, 3, 20, 0, 0), // Desenvolvimento com halteres (pegada neutra)

                    // Ficha B - Pull (Costas e Bíceps)
                    new CadastrarFichaExercicioDTO(7L, 9L, 2, 3, 20, 0, 0), // Puxador frontal pegada pronada
                    new CadastrarFichaExercicioDTO(7L, 10L, 2, 3, 20, 0, 0), // Puxador frontal unilateral
                    new CadastrarFichaExercicioDTO(7L, 11L, 2, 3, 20, 0, 0), // Remada cavalinho (pegada neutra)
                    new CadastrarFichaExercicioDTO(7L, 12L, 2, 3, 20, 0, 0), // Remada cavalinho (pegada pronada)
                    new CadastrarFichaExercicioDTO(7L, 13L, 2, 3, 20, 0, 0), // Rosca direta com barra W
                    new CadastrarFichaExercicioDTO(7L, 14L, 2, 3, 20, 0, 0), // Rosca scott com barra W
                    new CadastrarFichaExercicioDTO(7L, 15L, 2, 3, 20, 0, 0), // Rosca inversa com barra W
                    new CadastrarFichaExercicioDTO(7L, 16L, 2, 3, 20, 0, 0), // Barra Fixa
                    new CadastrarFichaExercicioDTO(7L, 17L, 2, 3, 20, 0, 0), // Face Pull
                    new CadastrarFichaExercicioDTO(7L, 18L, 2, 3, 20, 0, 0), // Pulldown

                    // Ficha C - Legs (Pernas)
                    new CadastrarFichaExercicioDTO(8L, 19L, 2, 3, 20, 0, 0), // Agachamento búlgaro
                    new CadastrarFichaExercicioDTO(8L, 20L, 2, 3, 20, 0, 0), // Agachamento smith
                    new CadastrarFichaExercicioDTO(8L, 21L, 2, 3, 20, 0, 0), // Agachamento sumô no smith
                    new CadastrarFichaExercicioDTO(8L, 22L, 2, 3, 20, 0, 0), // Cadeira extensora
                    new CadastrarFichaExercicioDTO(8L, 23L, 2, 3, 20, 0, 0), // Cadeira abdutora
                    new CadastrarFichaExercicioDTO(8L, 24L, 2, 3, 20, 0, 0), // Mesa flexora
                    new CadastrarFichaExercicioDTO(8L, 25L, 2, 3, 20, 0, 0), // Stiff
                    new CadastrarFichaExercicioDTO(8L, 26L, 2, 3, 20, 0, 0), // Panturrilha sentado máquina
                    new CadastrarFichaExercicioDTO(8L, 27L, 2, 3, 20, 0, 0), // Leg Press
                    new CadastrarFichaExercicioDTO(8L, 28L, 2, 3, 20, 0, 0), // Agachamento Frontal
                    new CadastrarFichaExercicioDTO(8L, 29L, 2, 3, 20, 0, 0), // Leg Curl
                    new CadastrarFichaExercicioDTO(8L, 30L, 2, 3, 20, 0, 0) // Avanço

            );

            fichaExercicios.forEach(fichaExercicioService::cadastrarFichaExercicio);
            System.out.println("FichasE cadastradas com sucesso!");
        }




        if (alimentoRepository.count() == 0) {
            List<Alimento> alimentos = List.of(
                    new Alimento("Frango grelhado", 0f, 0f, 0f, 0f, 0f),
                    new Alimento("Arroz integral", 0f, 0f, 0f, 0f, 0f),
                    new Alimento("Batata doce", 0f, 0f, 0f, 0f, 0f),
                    new Alimento("Ovos", 0f, 0f, 0f, 0f, 0f),
                    new Alimento("Iogurte", 0f, 0f, 0f, 0f, 0f),
                    new Alimento("Banana", 0f, 0f, 0f, 0f, 0f),
                    new Alimento("Aveia", 0f, 0f, 0f, 0f, 0f),
                    new Alimento("Peito de peru", 0f, 0f, 0f, 0f, 0f)
            );
            alimentoRepository.saveAll(alimentos);
            System.out.println("Alimentos cadastrados com sucesso!");
        }


        if (dietaRepository.count() == 0) {
            // Buscar o usuário
            Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioRepository.findByEmail("tcoutinhossilva@gmail.com"));

            // Verificar se o usuário foi encontrado
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get(); // Obtém o usuário

                // Criar a dieta inicialmente
                Dieta dieta = new Dieta(new CadastrarDietaDTO(
                        "Dieta de ganho de massa",
                        "tcoutinhossilva@gmail.com",
                        TipoDieta.GANHO_MASSA,
                        usuario
                ));

                // IDs das refeições que queremos adicionar
                List<Long> idsRefeicoes = List.of(1L, 2L);
                dieta.adicionarRefeicoesPorIds(idsRefeicoes, refeicaoRepository); // Adiciona as refeições à dieta

                // Salvar a dieta no repositório
                dietaRepository.save(dieta);
                System.out.println("Dieta cadastrada com sucesso!");
            } else {
                System.out.println("Usuário não encontrado! A dieta não foi cadastrada.");
            }
        }

        if (refeicaoRepository.count() == 0) {
            Long dietaId = 1L; // Substitua pelo ID correto que você deseja buscar

            Optional<Dieta> dietaOpt = dietaRepository.findById(dietaId);
            Dieta dieta = dietaOpt.get();

            List<Refeicao> refeicoes = List.of(
                    new Refeicao("Café da manhã", dieta),
                    new Refeicao("Almoço", dieta)
            );

            refeicaoRepository.saveAll(refeicoes); // Salva a lista de Refeição
            System.out.println("Refeições cadastradas com sucesso!");
        }

        //List<FichaExercicio> fichaExercicios =
        System.out.println(fichaExercicioRepository.findAll().stream().getClass());// Imprime cada FichaExercicioDTO

        if (refeicaoAlimentoRepository.count() == 0) {
            Optional<Refeicao> refeicaoOpt = refeicaoRepository.findById(1L);
            Optional<Alimento> alimentoOpt = alimentoRepository.findById(1L);
            if (refeicaoOpt.isPresent() && alimentoOpt.isPresent()) {
                Refeicao refeicao = refeicaoOpt.get();
                Alimento alimento = alimentoOpt.get();
                RefeicaoAlimento refeicaoAlimento = new RefeicaoAlimento(refeicao, alimento, 200f);
                refeicaoAlimentoRepository.save(refeicaoAlimento);
                System.out.println("RefeiçãoAlimento cadastrada com sucesso!");
            } else {
                System.out.println("Refeição ou Alimento não encontrado! RefeicaoAlimento não foi cadastrada.");
            }
        }
    }

    private List<Long> getExerciseIdsByName(List<String> exerciseNames) {
        return exerciseNames.stream()
                .map(name -> exercicioRepository.findByNome(name)
                        .map(exercicio -> exercicio.getId())
                        .orElseThrow(() -> new RuntimeException("Exercício não encontrado: " + name)))
                .collect(Collectors.toList());
    }

    private List<Long> getExerciseIds() {
        // Aqui você busca os IDs dos exercícios cadastrados
        return exercicioRepository.findAll().stream().map(Exercicio::getId).collect(Collectors.toList());
    }

    private List<Exercicio> getExerciciosByName() {
        // Aqui você pode ajustar a lógica para pegar os exercícios pelo nome, ao invés de seus IDs
        List<String> exerciseNames = Arrays.asList(
                "Supino reto", "Crucifixo com halteres", "Supino inclinado", // Adicione os nomes dos exercícios
                "Tríceps coice com halter", "Tríceps pulley corda"
                // Continue com os outros exercícios...
        );

        return exerciseNames.stream()
                .map(name -> exercicioRepository.findByNome(name)
                        .orElseThrow(() -> new RuntimeException("Exercício não encontrado: " + name))) // Busca o objeto Exercicio
                .collect(Collectors.toList());
    }

    private void adicionarExerciciosAFichas() {
        // Agora, associar exercícios às fichas
        fichaRepository.findAll().forEach(ficha -> {
            List<Exercicio> exercicios = getExerciciosByName(); // Buscar a lista de exercícios
            ficha.adicionarExercicios(exercicios); // Associar a lista de exercícios à ficha
            fichaRepository.save(ficha);
            System.out.println("Exercícios adicionados à ficha " + ficha.getNome());
        });
    }
}