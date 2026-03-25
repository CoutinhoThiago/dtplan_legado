import 'package:flutter/material.dart';

import '../../../services/treinos/treinos_service.dart';

class ListagemTreinosScreen extends StatefulWidget {
  @override
  _ListagemTreinosScreenState createState() => _ListagemTreinosScreenState();
}

class _ListagemTreinosScreenState extends State<ListagemTreinosScreen> {
  final TreinoService _treinoService = TreinoService();
  late Future<List<dynamic>> _futureTreinos;

  @override
  void initState() {
    super.initState();
    _futureTreinos = _treinoService.getTreinos();
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
        title: Text("Listagem de Treinos", style: TextStyle(color: Colors.amber[900])),
      ),
      body: FutureBuilder<List<dynamic>>(
        future: _futureTreinos,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text("Erro: ${snapshot.error}"));
          }

          if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Center(child: Text("Nenhum treino disponível"));
          }

          var treinos = snapshot.data!;
          return ListView.builder(
            itemCount: treinos.length,
            itemBuilder: (context, index) {
              var treino = treinos[index];
              return Card(
                elevation: 4.0,
                margin: EdgeInsets.all(8.0),
                child: ListTile(
                  title: Text(
                    treino['descricao'] ?? 'Sem descrição',
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                  subtitle: Text("Dia: ${treino['dia']}"),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
