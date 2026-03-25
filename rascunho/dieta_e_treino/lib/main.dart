import 'package:dieta_e_treino/providers/alimentos_provider.dart';
import 'package:dieta_e_treino/screens/refeicoes/alimentos/alimentos_cadastrados_screen.dart';
import 'package:dieta_e_treino/screens/treinos/exercicios/cadastrar_exercicios_scren.dart';
import 'package:dieta_e_treino/screens/treinos/cadastrar_plano_treino_screen.dart';
import 'package:dieta_e_treino/screens/treinos/exercicios/listar_exercicios_scren.dart';
import 'package:dieta_e_treino/screens/treinos/treinos/cadastrar_treino_screen.dart';
import 'package:dieta_e_treino/screens/treinos/treinos/listar_treinos_screen.dart';
import 'package:dieta_e_treino/screens/treinos/treinos_screen.dart';
import 'package:dieta_e_treino/services/treinos/exercicios_serivice.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'screens/home/home_screen.dart'; // A tela inicial

// Importe os arquivos de tela de cadastro aqui
import 'screens/refeicoes/refeicoes_screen.dart'; // A tela de listagem de refeições
import 'screens/refeicoes/cadastrar_refeicao_screen.dart'; // A tela de cadastro de refeições
import 'screens/refeicoes/alimentos/cadastrar_alimento_screen.dart'; // A tela de cadastro de alimentos

void main() {
  runApp(
    MyApp(),
  );
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Dieta e Treino',
      theme: ThemeData(
        primaryColor: Colors.blueGrey[900],
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      // Em vez de usar o atributo 'home', vamos definir nossas rotas nomeadas.
      initialRoute: '/', // O caminho '/' corresponde à tela inicial
      routes: {
        '/': (context) => HomeScreen(), // Esta é a sua tela inicial

        // Adicionando as rotas para as telas de cadastro
        '/cadastrar-refeicao': (context) => CadastrarRefeicaoScreen(),
        '/cadastrar-alimento': (context) => CadastrarAlimentoScreen(),

        '/cadastrar-exercicio': (context) => CadastrarExercicioScreen(),
        '/cadastrar-treino': (context) => CadastrarTreinoScreen(),
        '/cadastrar-plano-treino': (context) => CadastrarPlanoTreinoScreen(),

        // Adicionando as rotas para as telas de lsitegem
        '/refeicoes': (context) => RefeicoesScreen(), // Tela de listagem de refeições
        '/alimentos-cadastrados': (context) => AlimentosCadastradosScreen(),

        '/treinos': (context) => TreinosScreen(), // Tela de listagem de planos de treino
        '/treinos-cadastrados': (context) => ListagemTreinosScreen(),
        '/exercicios-cadastrados': (context) => ListagemExerciciosScreen(),

      },
    );
  }
}
