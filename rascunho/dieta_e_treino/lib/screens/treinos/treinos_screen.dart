import 'dart:io';
import 'package:flutter/material.dart';
import '../../models/plano_de_treino.dart';
import '../../services/treinos/plano_treino_service.dart';
import 'detalhes_plano_treino_screen.dart';

class TreinosScreen extends StatefulWidget {
  @override
  _TreinosScreenState createState() => _TreinosScreenState();
}

class _TreinosScreenState extends State<TreinosScreen> {
  final PlanoTreinoService _service = PlanoTreinoService();
  List<PlanoTreino> _planosTreino = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadTrainingPlans();
  }

  Future<void> _loadTrainingPlans() async {
    var plans = await _service.fetchPlanoDeTreinoService();
    setState(() {
      _planosTreino = plans;
      _isLoading = false;
    });
  }

  void _onFabPressed() {
    showModalBottomSheet(
        context: context,
        builder: (BuildContext bc) {
          return Container(
            child: Wrap(
              children: <Widget>[
                ListTile(
                  leading: Icon(Icons.add),
                  title: Text('Novo Plano de Treino'),
                  onTap: () {
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed('/cadastrar-plano-treino');
                  },
                ),
                ListTile(
                  leading: Icon(Icons.add),
                  title: Text('Cadastrar treino'),
                  onTap: () {
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed('/cadastrar-treino');
                  },
                ),
                ListTile(
                  leading: Icon(Icons.list),
                  title: Text('Treios cadastrados'),
                  onTap: () {
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed('/treinos-cadastrados');
                  },
                ),
                ListTile(
                  leading: Icon(Icons.add),
                  title: Text('Cadastrar Exercício'),
                  onTap: () {
                    Navigator.pop(context); // Fecha o menu
                    Navigator.of(context).pushNamed('/cadastrar-exercicio');
                  },
                ),
                ListTile(
                  leading: Icon(Icons.list),
                  title: Text('Exercícios cadastrados'),
                  onTap: () {
                    Navigator.pop(context); // Fecha o menu
                    Navigator.of(context).pushNamed('/exercicios-cadastrados');
                  },
                ),
              ],
            ),
          );
        }
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blueGrey[800],
        leading: Icon(
          Icons.fitness_center,
          color: Colors.amber[900],
        ),
        title: Text(
          "Planos de Treino",
          style: TextStyle(
            color: Colors.amber[900],
          ),
        ),
      ),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : ListView.separated(
        itemCount: _planosTreino.length,
        separatorBuilder: (context, index) => Divider(),
        itemBuilder: (context, index) {
          final plano = _planosTreino[index];
          return Card(
            margin: EdgeInsets.all(8.0),
            child: ListTile(
              leading: Icon(Icons.fitness_center),
              title: Text(
                plano.nome,
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  SizedBox(height: 2.0),
                  Text('Autor: ${plano.autor}'),
                  SizedBox(height: 2.0),
                  Text('Tipo: ${plano.tipo}')
                ],
              ),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => DetalhesPlanoTreinoScreen(plano: plano),
                  ),
                );
              },
            ),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _onFabPressed,
        child: Icon(Icons.add),
        backgroundColor: Colors.amber[900],
        tooltip: 'Mais opções',
      ),
    );
  }
}
