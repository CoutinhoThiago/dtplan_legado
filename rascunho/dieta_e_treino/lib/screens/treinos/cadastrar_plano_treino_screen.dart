import 'package:flutter/material.dart';

import '../../services/shared_preferences.dart';

class CadastrarPlanoTreinoScreen extends StatefulWidget {
  @override
  _CadastrarPlanoTreinoScreenState createState() => _CadastrarPlanoTreinoScreenState();
}

class _CadastrarPlanoTreinoScreenState extends State<CadastrarPlanoTreinoScreen> {
  final _formKey = GlobalKey<FormState>();

  final _nomeController = TextEditingController();

  final TextEditingController _autorController = TextEditingController();
  @override
  void initState() {
    super.initState();
    _loadAuthorName();
  }
  Future<void> _loadAuthorName() async {
    final authorName = await getAuthorName();
    setState(() {
      _autorController.text = authorName;
    });
  }

  String _tipoTreino = 'Musculação'; // Opções: 'Musculação', 'Aeróbico'

  @override
  void dispose() {
    _nomeController.dispose();
    _autorController.dispose();
    super.dispose();
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
          "Novo plano de treino",
          style: TextStyle(
            color: Colors.amber[900],
          ),
        ),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: <Widget>[
              TextFormField(
                controller: _nomeController,
                decoration: InputDecoration(labelText: 'Nome do Plano'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira o nome do plano de treino.';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _autorController,
                decoration: InputDecoration(labelText: 'Autor'),
                enabled: false, // Desabilitado para edição
              ),
              DropdownButtonFormField(
                value: _tipoTreino,
                onChanged: (String? newValue) {
                  setState(() {
                    _tipoTreino = newValue!;
                  });
                },
                items: <String>['Musculação', 'Aeróbico']
                    .map<DropdownMenuItem<String>>((String value) {
                  return DropdownMenuItem<String>(
                    value: value,
                    child: Text(value),
                  );
                }).toList(),
                decoration: InputDecoration(labelText: 'Tipo de Treino'),
              ),
              if (_tipoTreino == 'Musculação' || _tipoTreino == 'Aeróbico')
              // Aqui você pode inserir o widget para adicionar exercícios
              // Este widget deve filtrar e exibir exercícios com base no _tipoTreino
              // Por exemplo, um botão que abre uma tela/modal para escolher exercícios

                ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      // Implemente a lógica para salvar o plano de treino
                    }
                  },
                  child: Text('Salvar Plano de Treino'),
                ),
            ],
          ),
        ),
      ),
    );
  }
}
