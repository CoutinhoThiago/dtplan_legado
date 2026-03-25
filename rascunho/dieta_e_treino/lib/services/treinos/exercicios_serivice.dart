import 'dart:io';
import 'package:dieta_e_treino/services/perfil/auth_service.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import '../token_servicce.dart';

class ExerciseService {
  final String apiUrl = "http://10.0.2.2:8080/exercicios";

  Future<String> _checkAndRenewToken() async {
    String? token = await TokenService().getToken();
    if (token == null || !await TokenService().isTokenValid()) {
      token = await AuthService().login("admin", "123456");
      await TokenService().saveToken(token);
    }
    return token;
  }

  Future<http.Response?> postExercise(Map<String, dynamic> exerciseData) async {
    String token = await _checkAndRenewToken();

    try {
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
          'Authorization': 'Bearer $token',
        },
        body: jsonEncode(exerciseData),
      );

      if (response.statusCode == 201) {
        return response;
      } else {
        throw Exception('Erro ao enviar os dados do exercício: ${response.statusCode}');
      }
    } catch (e) {
      _handleException(e);
    }
  }

  Future<Map<String, dynamic>> getExercise() async {
    String token = await _checkAndRenewToken();

    try {
      final response = await http.get(
        Uri.parse(apiUrl),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        throw Exception('Erro ao carregar os exercícios: ${response.statusCode}');
      }
    } catch (e) {
      return {};
    }
  }

  void _handleException(e) {
    if (e is SocketException) {
      throw Exception('Erro de conexão: $e');
    } else if (e is http.ClientException) {
      throw Exception('Erro do cliente: $e');
    } else {
      throw Exception('Erro desconhecido: $e');
    }
  }
}
