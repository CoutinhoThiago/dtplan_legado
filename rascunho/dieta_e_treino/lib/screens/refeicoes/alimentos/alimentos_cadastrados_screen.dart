import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Alimentos Cadastrados',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: AlimentosCadastradosScreen(),
    );
  }
}

class AlimentosCadastradosScreen extends StatefulWidget {
  AlimentosCadastradosScreen({Key? key}) : super(key: key);

  @override
  _AlimentosCadastradosScreenState createState() => _AlimentosCadastradosScreenState();
}

class _AlimentosCadastradosScreenState extends State<AlimentosCadastradosScreen> {
  List<Map<String, dynamic>> alimentosCadastrados = [
    {"nome": "Ovos", "calorias": 155, "proteinas": 13, "carboidratos": 1.1, "gorduras": 11, "descricao": "Rico em proteínas e vitaminas."},
    {"nome": "Frango", "calorias": 239, "proteinas": 27, "carboidratos": 0, "gorduras": 14, "descricao": "Excelente fonte de proteínas magras."},
    {"nome": "Quinoa", "calorias": 120, "proteinas": 4.1, "carboidratos": 21.3, "gorduras": 1.9, "descricao": "Alto teor de proteínas e fibras."},
    // etc.
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Alimentos Cadastrados'),
      ),
      body: ListView.builder(
        itemCount: alimentosCadastrados.length,
        itemBuilder: (context, index) {
          var alimento = alimentosCadastrados[index];

          return Card(
            child: ListTile(
              title: Text(alimento['nome'] ?? 'Sem título'), // Exibe o nome do alimento
              subtitle: Text(alimento['descricao'] ?? 'Sem descrição'), // Exibe a descrição
            ),
          );
        },
      ),
    );
  }
}
