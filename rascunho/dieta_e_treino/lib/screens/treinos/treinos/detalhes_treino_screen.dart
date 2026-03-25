import 'package:flutter/material.dart';
import '../../../models/exercicio/ExercicioCardio.dart';
import '../../../models/exercicio/ExercicioMusculacao.dart';
import '../../../models/exercicio/exercicio.dart';
import '../../../models/treino.dart';

class DetalhesTreinoScreen extends StatefulWidget {
  final Treino treino;

  DetalhesTreinoScreen({required this.treino});

  @override
  _DetalhesTreinoScreenState createState() => _DetalhesTreinoScreenState();
}

class _DetalhesTreinoScreenState extends State<DetalhesTreinoScreen> {
  List<bool> exerciciosConcluidos = [];

  @override
  void initState() {
    super.initState();
    exerciciosConcluidos =
        List.generate(widget.treino.exercicios.length, (index) => false);
  }

  IconData getIconForExercicio(Exercicio exercicio) {
    if (exercicio is ExercicioMusculacao) {
      return Icons.fitness_center;
    } else if (exercicio is ExercicioCardio) {
      return Icons.directions_run;
    }
    return Icons.help_outline;
  }

  void finalizarTreino() {
    // Aqui, você implementaria a lógica para enviar os dados para o banco de dados
    // E depois disso, voltar para a tela anterior
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blueGrey[800],
        leading: IconButton(
          icon: Icon(Icons.arrow_back, color: Colors.amber[900]),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: Text(
          widget.treino.descricao,
          style: TextStyle(
            color: Colors.amber[900],
          ),
        ),
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: widget.treino.exercicios.length,
              itemBuilder: (context, index) {
                final exercicio = widget.treino.exercicios[index];
                String detalhes = exercicio is ExercicioMusculacao
                    ? '${exercicio.repeticoesMin} - ${exercicio.repeticoesMax} x ${exercicio.series}'
                    : '${exercicio.duracaoMinutos} min';

                return Card(
                  margin: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
                  child: ListTile(
                    leading: Icon(getIconForExercicio(exercicio)),
                    title: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Text(
                          exercicio.descricao,
                          style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                            decoration: exerciciosConcluidos[index]
                                ? TextDecoration.lineThrough
                                : null,
                          ),
                        ),
                        SizedBox(height: 4.0),
                        // Espaçamento entre título e subtítulo
                        Text(
                          detalhes,
                          style: TextStyle(
                            fontSize: 14,
                            color: Colors.grey,
                          ),
                        ),
                      ],
                    ),
                    trailing: Checkbox(
                      value: exerciciosConcluidos[index],
                      onChanged: (value) {
                        setState(() {
                          exerciciosConcluidos[index] = value!;
                        });
                      },
                    ),
                  ),
                );
              },
            ),
          ),
          Padding(
            padding: EdgeInsets.symmetric(vertical: 12.0, horizontal: 16.0),
            child: ElevatedButton(
              onPressed: finalizarTreino,
              child: Text('Finalizar Treino'),
              style: ElevatedButton.styleFrom(
                minimumSize: Size(double.infinity, 50), // Tamanho do botão
              ),
            ),
          ),
        ],
      ),
    );
  }
}