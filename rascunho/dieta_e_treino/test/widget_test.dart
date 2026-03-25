// Primeiro, importamos os pacotes necessários para o teste.
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

// Importamos o arquivo onde está localizado o widget principal do aplicativo.
import 'package:dieta_e_treino/main.dart'; // Certifique-se de que o caminho esteja correto.

// A função principal que executa o teste.
void main() {
  // Aqui definimos um grupo de testes chamado 'Counter increments smoke test'.
  testWidgets('Counter increments smoke test', (WidgetTester tester) async {
    // "pumpWidget" é usado para construir e renderizar o widget MyApp.
    await tester.pumpWidget(MyApp());

    // Verifique se o nosso contador começa em 0. "findsOneWidget" verifica se exatamente um widget que cumpre a condição é encontrado.
    expect(find.text('0'), findsOneWidget);
    // Verifique se não existe nenhum widget com o texto '1' antes de pressionar o botão.
    expect(find.text('1'), findsNothing);

    // Interaja com o aplicativo. Estamos simulando um toque no widget que tem o ícone de adição ('+').
    await tester.tap(find.byIcon(Icons.add));
    // Após a interação, é necessário reconstruir o widget para ver a mudança de estado.
    // "pump" induz o framework a processar um novo quadro, o que causa a reconstrução do widget com o novo estado.
    await tester.pump();

    // Agora que o botão foi pressionado, o contador deve ter incrementado de 0 para 1.
    // Verifique se o texto '0' não é mais encontrado no widget.
    expect(find.text('0'), findsNothing);
    // E agora deve haver um widget com o texto '1'.
    expect(find.text('1'), findsOneWidget);
  });
}
