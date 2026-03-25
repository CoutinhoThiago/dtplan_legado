import 'package:flutter/material.dart';

import '../../../services/treinos/exercicios_serivice.dart';

class CadastrarExercicioScreen extends StatefulWidget {
  @override
  _CadastrarExercicioScreenState createState() => _CadastrarExercicioScreenState();
}

class _CadastrarExercicioScreenState extends State<CadastrarExercicioScreen> {
  final _formKey = GlobalKey<FormState>();

  final _descricaoController = TextEditingController();
  String _tipoExercicio = 'Musculação'; // Opções: 'Musculação', 'Aeróbico'
  final _musculoAlvoController = TextEditingController();
  final _seriesController = TextEditingController();
  final _repeticoesMinController = TextEditingController();
  final _repeticoesMaxController = TextEditingController();
  final _cargaController = TextEditingController();
  final _duracaoController = TextEditingController();
  final _intensidadeController = TextEditingController();

  int ? _selectedSeries;

  final exerciseService = ExerciseService();

  @override
  void dispose() {
    _descricaoController.dispose();
    _musculoAlvoController.dispose();
    _seriesController.dispose();
    _repeticoesMinController.dispose();
    _repeticoesMaxController.dispose();
    _cargaController.dispose();
    _duracaoController.dispose();
    _intensidadeController.dispose();
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
          "Cadastrar exercicio",
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
              if (_tipoExercicio == 'Musculação')
                TextFormField(
                  controller: _musculoAlvoController,
                  decoration: InputDecoration(
                      labelText: 'Músculo Alvo'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Por favor, insira o músculo alvo.';
                    }
                    return null;
                  },
                ),
              if (_tipoExercicio == 'Musculação')
                DropdownButtonFormField<int>(
                  value: _selectedSeries,
                  decoration: InputDecoration(
                    labelText: 'Séries',
                  ),
                  items: <int>[1, 2, 3, 4, 5, 6]
                      .map<DropdownMenuItem<int>>((int value) {
                    return DropdownMenuItem<int>(
                      value: value,
                      child: Text(value.toString()),
                    );
                  }).toList(),
                  onChanged: (int? newValue) {
                    setState(() {
                      _selectedSeries = newValue!;
                    });
                  },
                  validator: (value) {
                    if (value == null) {
                      return 'Por favor, selecione uma série.';
                    }
                    return null;
                  },
                ),
              if (_tipoExercicio == 'Musculação')
                TextFormField(
                  keyboardType: TextInputType.number,
                  controller: _repeticoesMinController,
                  decoration: InputDecoration(
                    labelText: 'Repetições Mínimas',
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Por favor, insira o número mínimo de repetições.';
                    }
                    return null;
                  },
                ),
              if (_tipoExercicio == 'Musculação')
                TextFormField(
                  keyboardType: TextInputType.number,
                  controller: _repeticoesMaxController,
                  decoration: InputDecoration(
                    labelText: 'Repetições Máximas (Opcional)',
                  ),
                ),
              if (_tipoExercicio == 'Musculação')
                TextFormField(
                  keyboardType: TextInputType.number,
                  controller: _cargaController,
                  decoration: InputDecoration(
                      labelText: 'Carga'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Por favor, insira a carga inicial.';
                    }
                    return null;
                  },
                ),
              if (_tipoExercicio == 'Aeróbico')
                TextFormField(
                  keyboardType: TextInputType.number,
                  controller: _duracaoController,
                  decoration: InputDecoration(
                      labelText: 'Duração'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Por favor, insira a duração.';
                    }
                    return null;
                  },
                ),
              if (_tipoExercicio == 'Aeróbico')
                TextFormField(
                  keyboardType: TextInputType.number,
                  controller: _intensidadeController,
                  decoration: InputDecoration(
                      labelText: 'Intensidade'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Por favor, insira a intensidade.';
                    }
                    return null;
                  },
                ),
              ElevatedButton(
                onPressed: () async {
                  if (_formKey.currentState!.validate()) {
                    try {
                      Map<String, dynamic> exerciseData = {
                        "descricao": _descricaoController.text,
                        "ativo": true,
                        "tipo": _tipoExercicio == 'Musculação' ? 1 : 2,
                      };

                      if (_tipoExercicio == 'Musculação') {
                        exerciseData.addAll({
                          "musculo_alvo": _musculoAlvoController.text,
                          "series": _selectedSeries,
                          "repeticoes_min": int.tryParse(_repeticoesMinController.text),
                          "repeticoes_max": int.tryParse(_repeticoesMaxController.text),
                          "carga": double.tryParse(_cargaController.text),
                        });
                      } else if (_tipoExercicio == 'Aeróbico') {
                        exerciseData.addAll({
                          "duracao_minutos": int.tryParse(_duracaoController.text),
                          "intensidade": int.tryParse(_intensidadeController.text),
                        });
                      }

                      final response = await exerciseService.postExercise(exerciseData);

                      if (response != null) {
                        print('Código de Status: ${response.statusCode}');
                        if (response.statusCode == 201) {
                          print('Exercício salvo com sucesso!');
                        } else {
                          print('Erro ao salvar: Código de Status ${response.statusCode}');
                        }
                      } else {
                        print('Resposta é null, verifique a requisição.');
                      }
                    } catch (e) {
                      print('Erro ao salvar exercício: $e');
                    }
                    Navigator.of(context).pushNamed('/exercicios-cadastrados');
                  }
                },
                child: Text('Salvar Exercício'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}


