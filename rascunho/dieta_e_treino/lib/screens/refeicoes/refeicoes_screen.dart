import 'package:flutter/material.dart';

import '../../services/refeicoes/alimentos_service.dart';
import 'alimentos/cadastrar_alimento_screen.dart';
import 'cadastrar_refeicao_screen.dart';
import 'alimentos/alimentos_cadastrados_screen.dart';

class RefeicoesScreen extends StatefulWidget {
  @override
  _RefeicoesScreenState createState() => _RefeicoesScreenState();
}

class _RefeicoesScreenState extends State<RefeicoesScreen> {
  final AlimentosService _service = AlimentosService();
  List<Map<String, dynamic>> _refeicoes = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _carregarRefeicoes();
  }

  Future<void> _carregarRefeicoes() async {
    try {
      var refeicoesCadastradas = await _service.obterAlimentosCadastrados();
      setState(() {
        _refeicoes = refeicoesCadastradas;
        _isLoading = false;
      });
    } catch (erro) {
      print("Erro ao carregar refeições: $erro");
    }
  }
  void _onFabPressed() {
    showModalBottomSheet(
        context: context,
        builder: (BuildContext bc) {
          return Container(
            child: Wrap(
              children: <Widget>[
                ListTile(
                  leading: Icon(Icons.fitness_center),
                  title: Text('Cadastrar Alimento'),
                  onTap: () {
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed('/cadastrar-alimento');
                  },
                ),
                ListTile(
                  leading: Icon(Icons.list),
                  title: Text('Alimentos Cadastrados'),
                  onTap: () {
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed('/alimentos cadastrados');
                  },
                ),
                ListTile(
                  leading: Icon(Icons.list),
                  title: Text('Nova Refeição'),
                  onTap: () {
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed('/cadastrar-refeicao').then((resultado) {
                      if (resultado != null && resultado is Map<String, dynamic>) {
                        setState(() {
                          _refeicoes.add(resultado);
                        });
                      }
                    });
                  },
                ),
              ],
            ),
          );
        }
    );
  }

  void _excluirRefeicao(int index) {
    setState(() {
      _refeicoes.removeAt(index);
    });
  }

  void _confirmarExclusao(int index) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Excluir Refeição'),
          content: Text('Você tem certeza que deseja excluir esta refeição?'),
          actions: <Widget>[
            TextButton(
              child: Text('Cancelar'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('Excluir'),
              onPressed: () {
                _excluirRefeicao(index);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blueGrey[800],
        leading: Icon(
          Icons.restaurant,
          color: Colors.amber[900],
        ),
        title: Text(
          "Refeições",
          style: TextStyle(color: Colors.amber[900]),
        ),
      ),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
        itemCount: _refeicoes.length,
        itemBuilder: (context, index) {
          var refeicao = _refeicoes[index];
          return GestureDetector(
            onLongPress: () => _confirmarExclusao(index),
            child: Card(
              child: ListTile(
                title: Text(refeicao['titulo'] ?? 'Sem título'),
              ),
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
