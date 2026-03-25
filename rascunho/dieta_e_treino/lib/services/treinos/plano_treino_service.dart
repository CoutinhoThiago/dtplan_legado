import '../../models/exercicio/ExercicioCardio.dart';
import '../../models/exercicio/ExercicioMusculacao.dart';
import '../../models/exercicio/exercicio.dart';
import '../../models/plano_de_treino.dart';
import '../../models/treino.dart';

class PlanoTreinoService {
  Future<List<PlanoTreino>> fetchPlanoDeTreinoService() async {
    // Simula um atraso de rede
    await Future.delayed(Duration(seconds: 2));

    // Dados fictícios para o plano de treino
    List<PlanoTreino> mockPlans = [
      PlanoTreino(
        nome: 'Treino ABCDEF',
        autor: 'Thiago Coutinho',
        tipo: 'Musculação',
        treinos: [
          Treino(
            descricao: 'Costas, Bíceps e Antebraço (Romulo)',
            dia: 'Dia específico',
            exercicios: [
              ExercicioCardio(
                descricao: 'Escada',
                duracaoMinutos: 5,
                intensidade: 5,
              ),
              ExercicioMusculacao(
                descricao: 'Puxador frente supinado',
                musculoAlvo: 'Costas - Latíssimo do Dorso',
                series: 3,
                repeticoesMin: 8,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Pull down',
                musculoAlvo: 'Costas - Latíssimo do Dorso',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Puxador frente pronado',
                musculoAlvo: 'Costas - Latíssimo do Dorso',
                series: 3,
                repeticoesMin: 8,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Remada curvado pronada',
                musculoAlvo: 'Romboides',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Rosca direta com barra',
                musculoAlvo: 'Bíceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Rosca direta com giro',
                musculoAlvo: 'Bíceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Rosca martelo',
                musculoAlvo: 'Bíceps - Cabeça Longa',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Rosca inversa',
                musculoAlvo: 'Antebraço',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
            ],
          ),
          Treino(
            descricao: 'Peito e Tríceps',
            dia: 'Dia específico',
            exercicios: [
              ExercicioCardio(
                descricao: 'Escada',
                duracaoMinutos: 5,
                intensidade: 5,
              ),
              ExercicioMusculacao(
                descricao: 'Supino reto',
                musculoAlvo: 'Peito - Porção Média',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Supino inclinado',
                musculoAlvo: 'Peito - Porção Superior',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Crucifixo',
                musculoAlvo: 'Peito - Porção Superior',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Triceps banco',
                musculoAlvo: 'Tríceps - Cabeça Longa',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Tríceps testa (barra H)',
                musculoAlvo: 'Tríceps - Cabeça Longa',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Tríceps corda',
                musculoAlvo: 'Tríceps - Cabeça Lateral',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Tríceps Pulley (pegada invertida)',
                musculoAlvo: 'Tríceps - Cabeça Medial',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Abdominal máquina',
                musculoAlvo: 'Abdominal',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
            ],
          ),
          Treino(
            descricao: 'Perna 1 (Romulo)',
            dia: 'Dia específico',
            exercicios: [
              ExercicioCardio(
                descricao: 'Esteira',
                duracaoMinutos: 5,
                intensidade: 5,
              ),
              ExercicioMusculacao(
                descricao: 'Leg 45',
                musculoAlvo: 'Quadríceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Agachamento com bola',
                musculoAlvo: 'Quadríceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Leg 45 aberto',
                musculoAlvo: 'Quadríceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Agachamento sumô',
                musculoAlvo: 'Quadríceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Cadeira abdutora',
                musculoAlvo: 'Abdutores',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Cadeira abdutora inclinada',
                musculoAlvo: 'Abdutores',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Stiff',
                musculoAlvo: 'Posterior de coxa',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Elevação pélvica',
                musculoAlvo: 'Posterior de coxa',
                series: 3,
                repeticoesMin: 8,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Panturrilha',
                musculoAlvo: 'Panturrilha',
                series: 3,
                repeticoesMin: 15,
                repeticoesMax: 30,
                carga: 0,
              ),
            ],
          ),
          Treino(
            descricao: 'Costas, Ombro e Bíceps',
            dia: 'Dia específico',
            exercicios: [
              ExercicioCardio(
                descricao: 'Escada',
                duracaoMinutos: 5,
                intensidade: 5,
              ),
              ExercicioMusculacao(
                descricao: 'Puxador frente supinado',
                musculoAlvo: 'Costas - Latíssimo do Dorso',
                series: 3,
                repeticoesMin: 8,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Puxador frente neutro',
                musculoAlvo: 'Costas - Latíssimo do Dorso',
                series: 3,
                repeticoesMin: 8,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Remada neutra',
                musculoAlvo: 'Costas - Latíssimo do Dorso',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Crucifixo inverso',
                musculoAlvo: 'Ombro - Porção Posterior',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Encolhimento de trapézio',
                musculoAlvo: 'Trapézio - Porção Superior',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 20,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Rosca direta com barra',
                musculoAlvo: 'Bíceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Rosca direta com giro',
                musculoAlvo: 'Bíceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Rosca martelo',
                musculoAlvo: 'Bíceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Abdominal máquina',
                musculoAlvo: 'Abdominal',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
            ],
          ),
          Treino(
            descricao: 'Peito e Ombro (Romulo)',
            dia: 'Dia específico',
            exercicios: [
              ExercicioCardio(
                descricao: 'Escada',
                duracaoMinutos: 5,
                intensidade: 5,
              ),
              ExercicioMusculacao(
                descricao: 'Supino reto',
                musculoAlvo: 'Peito - Porção Média',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Supino declinado',
                musculoAlvo: 'Peito - Porção Superior',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Supino inclinado',
                musculoAlvo: 'Peito - Porção Inferior',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Crucifixo',
                musculoAlvo: 'Peito - Porção Média',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Elevação lateral com halter',
                musculoAlvo: 'Ombro - Deltoide Porção Medial',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Elevação lateral no cabo',
                musculoAlvo: 'Ombro - Deltoide Porção Medial',
                series: 3,
                repeticoesMin: 8,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Elevação frontal',
                musculoAlvo: 'Ombro - Deltoide Porção Anterior',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
            ],
          ),
          Treino(
            descricao: 'Perna 1 (Alicia)',
            dia: 'Dia específico',
            exercicios: [
              ExercicioCardio(
                descricao: 'Escada',
                duracaoMinutos: 5,
                intensidade: 5,
              ),
              ExercicioMusculacao(
                descricao: 'Agachamento smith',
                musculoAlvo: 'Quadríceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Leg press',
                musculoAlvo: 'Quadríceps',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Cadeira extensora',
                musculoAlvo: 'Vasto lateral',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Cadeira abdutora',
                musculoAlvo: 'Abdutores',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Cadeira abdutora inclinada',
                musculoAlvo: 'Abdutores',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Elevação pélvica máquina',
                musculoAlvo: 'Glúteos',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 12,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Cadeira flexora',
                musculoAlvo: 'Posterior de coxa',
                series: 3,
                repeticoesMin: 10,
                repeticoesMax: 15,
                carga: 0,
              ),
              ExercicioMusculacao(
                descricao: 'Panturrilha',
                musculoAlvo: 'Panturrilha',
                series: 3,
                repeticoesMin: 15,
                repeticoesMax: 30,
                carga: 0,
              ),
            ],
          ),
        ],
      ),
      PlanoTreino(
        nome: 'HIIT',
        autor: 'Instrutor(a)',
        tipo: 'Cardio',
        treinos: [
          Treino(
          descricao: 'HIIT Esteira',
          dia: 'Dia específico',
            exercicios: List<Exercicio>.generate(20, (index) {
            // Alternar entre tiro e recuperação
              return index % 2 == 0
              ? ExercicioCardio(
                descricao: 'Tiro na Esteira',
                duracaoMinutos: 1, // Duração do tiro
                intensidade: 8, // Velocidade durante o tiro
              )
              : ExercicioCardio(
                descricao: 'Recuperação na Esteira',
                duracaoMinutos: 0.5, // Duração da recuperação
                intensidade: 3, // Velocidade durante a recuperação
              );
            }),
          ),
        ],
      ),
    ];

    return mockPlans;
  }
}
