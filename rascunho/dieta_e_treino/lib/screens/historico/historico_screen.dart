import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';

class HistoricoScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blueGrey[800],
        leading: Icon(
          Icons.history,
          color: Colors.amber[900],
        ),
        title: Text(
            "Histórico",
            style: TextStyle(
            color: Colors.amber[900],
          ),
        ),
      ),
      body: ListView(
        children: [
          GraficoCard(
            titulo: 'Gráfico de Peso',
            child: Placeholder(),
            onTap: () {
              print('Gráfico de peso tocado');
            },
          ),
          GraficoCard(
            titulo: 'Gráfico de Água',
            child: Placeholder(),
            onTap: () {
              print('Gráfico de água tocado');
            },
          ),
        ],
      ),
    );
  }
}

class GraficoCard extends StatelessWidget {
  final String titulo;
  final Widget child;
  final VoidCallback onTap;

  const GraficoCard({
    Key? key,
    required this.titulo,
    required this.child,
    required this.onTap,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        height: 200,
        child: Card(
          child: Padding(
            padding: EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  titulo,
                  style: Theme.of(context).textTheme.headline6,
                ),
                Expanded(
                  child: Center(
                    child: child,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
