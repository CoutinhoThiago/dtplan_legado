import 'package:flutter/material.dart';

import '../../../services/treinos/treinos_service.dart';

class CadastrarTreinoScreen extends StatefulWidget {
  @override
  _CadastrarTreinoScreenState createState() => _CadastrarTreinoScreenState();
}

class _CadastrarTreinoScreenState extends State<CadastrarTreinoScreen> {
  final treinoService = TreinoService();

  final _formKey = GlobalKey<FormState>();
  final _descricaoController = TextEditingController();
  final _autorController = TextEditingController();
  String _tipoExercicio = 'Musculação'; // Opções: 'Musculação', 'Aeróbico'

  @override
  void dispose() {
    _descricaoController.dispose();
    _autorController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Cadastrar Treino'),
      ),
      body: Form(
        key: _formKey,
        child: ListView(
          padding: EdgeInsets.all(16.0),
          children: <Widget>[
            TextFormField(
              controller: _autorController,
              decoration: InputDecoration(
                  labelText: 'Autor'),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Por favor, insira o autor';
                }
                return null;
              },
            ),
            TextFormField(
              controller: _descricaoController,
              decoration: InputDecoration(
                  labelText: 'Descrição do Exercício'),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Por favor, insira a descrição do exercício.';
                }
                return null;
              },
            ),
            DropdownButtonFormField(
              value: _tipoExercicio,
              onChanged: (String? newValue) {
                setState(() {
                  _tipoExercicio = newValue!;
                });
              },
              items: <String>['Musculação', 'Aeróbico']
                  .map<DropdownMenuItem<String>>((String value) {
                return DropdownMenuItem<String>(
                  value: value,
                  child: Text(value),
                );
              }).toList(),
              decoration: InputDecoration(labelText: 'Tipo de Exercício'),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () async {
                if (_formKey.currentState!.validate()) {
                  try {
                    Map<String, dynamic> treinoData = {
                      "descricao": _descricaoController,
                      "autor": _autorController,
                      "tipo": _tipoExercicio == 'Musculação' ? 1 : 2,
                      "exercicios_id": [1],
                    };

                    final response = await treinoService.postTreino(treinoData);

                    if (response != null) {
                      print('Código de Status: ${response.statusCode}');
                      if (response.statusCode == 201) {
                        print('Treino salvo com sucesso!');
                      } else {
                        print('Erro${response.statusCode}');
                      }
                    } else {
                      print('Resposta é null, verifique a requisição.');
                    }
                  } catch (e) {
                    print('Erro $e');
                  }
                  Navigator.of(context).pushNamed('/treinos-cadastrados');
                }
              },
              child: Text('Salvar Exercício'),
            ),
          ],
        ),
      ),
    );
  }
}
