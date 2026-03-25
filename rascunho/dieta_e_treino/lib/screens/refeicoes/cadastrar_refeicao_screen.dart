import 'package:flutter/material.dart';

import '../../models/alimento.dart';
import '../../providers/refeicoes_provider.dart';

class CadastrarRefeicaoScreen extends StatefulWidget {
  @override
  _CadastrarRefeicaoScreenState createState() => _CadastrarRefeicaoScreenState();
}

class _CadastrarRefeicaoScreenState extends State<CadastrarRefeicaoScreen> {
  final _nomeRefeicaoController = TextEditingController();
  List<Alimento> _alimentosSelecionados = [];

  final List<Alimento> _alimentosDisponiveis = [
    Alimento(nome: 'Banana', calorias: 89, proteina: 1.1, carboidrato: 23, gordura: 0.3, porcao: ''),
    Alimento(nome: 'Ovo', calorias: 155, proteina: 13, carboidrato: 1.1, gordura: 11, porcao: ''),
  ];

  void _adicionarAlimento(Alimento alimento) {
    setState(() {
      _alimentosSelecionados.add(alimento);
    });
  }

  void _cadastrarRefeicao() {
    if (_nomeRefeicaoController.text.isEmpty || _alimentosSelecionados.isEmpty) {
      return;
    }

    final novaRefeicao = Refeicao(
      nome: _nomeRefeicaoController.text,
      alimentos: _alimentosSelecionados,
    );

    print(novaRefeicao.toString());

    _nomeRefeicaoController.clear();
    _alimentosSelecionados.clear();
    Navigator.of(context).pop();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Cadastrar Refeição'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextField(
              controller: _nomeRefeicaoController,
              decoration: InputDecoration(labelText: 'Nome da Refeição'),
            ),
            SizedBox(height: 20),
            Text('Selecionar Alimentos:', style: TextStyle(fontWeight: FontWeight.bold)),
            Expanded(
              child: ListView.builder(
                itemCount: _alimentosDisponiveis.length,
                itemBuilder: (context, index) {
                  final alimento = _alimentosDisponiveis[index];
                  return CheckboxListTile(
                    title: Text(alimento.nome),
                    subtitle: Text('${alimento.calorias} calorias'),
                    value: _alimentosSelecionados.contains(alimento),
                    onChanged: (bool? selected) {
                      if (selected != null && selected) {
                        _adicionarAlimento(alimento);
                      } else {
                        setState(() {
                          _alimentosSelecionados.remove(alimento);
                        });
                      }
                    },
                  );
                },
              ),
            ),
            ElevatedButton(
              onPressed: _cadastrarRefeicao,
              child: Text('Cadastrar Refeição'),
            ),
          ],
        ),
      ),
    );
  }
}
