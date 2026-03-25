import 'package:flutter/material.dart';
import '../../models/plano_de_treino.dart';
import 'treinos/detalhes_treino_screen.dart';

class DetalhesPlanoTreinoScreen extends StatelessWidget {
  final PlanoTreino plano;

  DetalhesPlanoTreinoScreen({required this.plano});

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
          plano.nome,
          style: TextStyle(
            color: Colors.amber[900],
          ),
        ),
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '${plano.nome}',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                //color: Colors.blueGrey,
              ),
            ),
            SizedBox(height: 10.0),
            Text(
              'Autor: ${plano.autor}',
              style: TextStyle(fontSize: 18, color: Colors.black54),
            ),
            SizedBox(height: 5.0),
            Text(
              'Tipo: ${plano.tipo}',
              style: TextStyle(fontSize: 18, color: Colors.black54),
            ),
            SizedBox(height: 20.0),
            Divider(),
            SizedBox(height: 10.0),
            Text(
              'Treinos:',
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold), //, color: Colors.blueGrey),
            ),
            SizedBox(height: 10.0),
            ...plano.treinos.map((treino) => Card(
              child: ListTile(
                title: Text(
                  treino.descricao,
                  style: TextStyle(fontSize: 16),
                ),
                trailing: Icon(Icons.arrow_forward_ios, size: 16),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => DetalhesTreinoScreen(treino: treino),
                    ),
                  );
                },
              ),
            )).toList(),
          ],
        ),
      ),
    );
  }
}
