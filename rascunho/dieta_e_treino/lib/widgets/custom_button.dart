import 'package:flutter/material.dart';

class CustomButton extends StatelessWidget {
  // Valores finais para tornar o botão mais personalizável
  final VoidCallback onPressed; // Ação que ocorrerá quando o botão for pressionado
  final String text; // Texto dentro do botão
  final Color color; // Cor do botão (opcional)
  final Color textColor; // Cor do texto dentro do botão (opcional)
  final double fontSize; // Tamanho da fonte do texto (opcional)
  final EdgeInsetsGeometry padding; // Espaçamento interno do botão (opcional)

  // O construtor aqui é usado para passar dados quando você inicializa o 'CustomButton'
  CustomButton({
    required this.onPressed,
    required this.text,
    this.color = Colors.blue, // Um valor padrão é fornecido se não especificado durante a inicialização
    this.textColor = Colors.white, // Valor padrão é branco se não for especificado
    this.fontSize = 16.0, // Valor padrão é 16 se não for especificado
    this.padding = const EdgeInsets.all(12.0), // Padding padrão se não for especificado
  });

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: onPressed, // Referenciando a variável final da classe
      style: ElevatedButton.styleFrom(
        primary: color, // Define a cor de fundo do botão
        padding: padding, // Aplica o espaçamento interno ao botão
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(8), // Raio do canto do botão
        ),
      ),
      child: Text(
        text, // O texto que será exibido no botão
        style: TextStyle(
          color: textColor, // Cor do texto
          fontSize: fontSize, // Tamanho da fonte do texto
        ),
      ),
    );
  }
}
