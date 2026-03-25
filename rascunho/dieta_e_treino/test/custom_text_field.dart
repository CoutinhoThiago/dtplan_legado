import 'package:flutter/material.dart';

class CustomTextField extends StatelessWidget {
  final TextEditingController controller; // Controlador para o valor do texto
  final String hintText; // Texto de dica a ser mostrado no campo
  final bool obscureText; // Se o texto deve ser escondido (bom para senhas)
  final TextInputType keyboardType; // Tipo do teclado (input), ex: texto, email, número
  final TextInputAction textInputAction; // Ação do botão 'enter' do teclado (ex: next, done)
  final ValueChanged<String>? onSubmitted; // Callback para quando o texto é submetido (enter é pressionado)
  final FormFieldValidator<String>? validator; // Função de validação para o campo

  // O construtor aqui é usado para passar os dados quando você inicializa o 'CustomTextField'
  CustomTextField({
    required this.controller,
    required this.hintText,
    this.obscureText = false, // Por padrão, o texto não é obscuro
    this.keyboardType = TextInputType.text, // Por padrão, o teclado é do tipo texto
    this.textInputAction = TextInputAction.done, // Por padrão, a ação será 'done'
    this.onSubmitted,
    this.validator,
  });

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      controller: controller, // Referenciando a variável final da classe
      decoration: InputDecoration(
        hintText: hintText, // Texto de dica
        contentPadding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0), // Padding interno do campo de texto
        border: OutlineInputBorder( // Estilo da borda quando não está focado
          borderRadius: BorderRadius.all(Radius.circular(5.0)),
        ),
        enabledBorder: OutlineInputBorder( // Estilo da borda quando está habilitado mas sem foco
          borderSide: BorderSide(color: Colors.blueAccent, width: 1.0),
          borderRadius: BorderRadius.all(Radius.circular(5.0)),
        ),
        focusedBorder: OutlineInputBorder( // Estilo da borda quando está focado
          borderSide: BorderSide(color: Colors.blueAccent, width: 2.0),
          borderRadius: BorderRadius.all(Radius.circular(5.0)),
        ),
      ),
      obscureText: obscureText, // Se o texto deve ser escondido ou não
      keyboardType: keyboardType, // O tipo de teclado
      textInputAction: textInputAction, // Ação para o botão 'enter' do teclado
      onFieldSubmitted: onSubmitted, // Callback para quando o campo é submetido
      validator: validator, // Função de validação
    );
  }
}
