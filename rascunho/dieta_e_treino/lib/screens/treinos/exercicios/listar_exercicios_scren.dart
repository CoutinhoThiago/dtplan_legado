import 'package:flutter/material.dart';
import '../../../services/treinos/exercicios_serivice.dart';

class ListagemExerciciosScreen extends StatefulWidget {
  @override
  _ListagemExerciciosScreenState createState() => _ListagemExerciciosScreenState();
}

class _ListagemExerciciosScreenState extends State<ListagemExerciciosScreen> {
  final ExerciseService _exerciseService = ExerciseService();
  late Future<Map<String, dynamic>> _futureExercises = _exerciseService.getExercise();

  @override
  void initState() {
    super.initState();
    _futureExercises = _exerciseService.getExercise();
  }

  Icon _getExerciseIcon(dynamic tipo) {
    final int tipoNumerico = tipo != null ? int.tryParse(tipo.toString()) ?? 0 : 0;
    return tipoNumerico == 2 ? Icon(Icons.directions_run) : Icon(Icons.fitness_center);
  }

  String _getSubtitle(dynamic exercise) {
    final int tipoNumerico = exercise['tipo'] != null ? int.tryParse(exercise['tipo'].toString()) ?? 0 : 0;
    return tipoNumerico == 2
        ? 'Tipo: Aeróbico\nDuração: ${exercise['duracao'] ?? 'N/A'}'
        : 'Tipo: Musculação\nMusculo alvo: ${exercise['musculo_alvo'] ?? 'N/A'}';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blueGrey[800],
        leading: IconButton(
          icon: Icon(Icons.arrow_back, color: Colors.amber[900]),
          onPressed: () => Navigator.of(context).pop(),
        ),
        title: Text("Listagem de exercícios", style: TextStyle(color: Colors.amber[900])),
      ),
      body: FutureBuilder<Map<String, dynamic>>(
        future: _futureExercises,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text("Erro: ${snapshot.error}"));
          }

          if (!snapshot.hasData || snapshot.data!['content'] == null) {
            return Center(child: Text("Nenhum dado disponível"));
          }

          var exercises = snapshot.data!['content'] as List<dynamic>;
          return ListView.builder(
            itemCount: exercises.length,
            itemBuilder: (context, index) {
              var exercise = exercises[index];
              return Card(
                elevation: 4.0,
                margin: EdgeInsets.all(8.0),
                child: ListTile(
                  leading: _getExerciseIcon(exercise['tipo']),
                  title: Text(
                    exercise['descricao'] ?? 'Sem descrição',
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                  subtitle: Text(_getSubtitle(exercise)),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
