import 'dart:convert';
import 'package:http/http.dart' as http;
import '../perfil/auth_service.dart';
import '../token_servicce.dart';

class TreinoService {
  final String apiUrl = "http://10.0.2.2:8080/treinos";
  final TokenService _tokenService = TokenService();

  Future<String> _getValidToken() async {
    String? token = await _tokenService.getToken();
    if (token == null || !await _tokenService.isTokenValid()) {
      token = await AuthService().login("admin", "123456");
      await _tokenService.saveToken(token); // Salva o novo token
    }
    return token;
  }

  Future<http.Response> postTreino(Map<String, dynamic> treinoData) async {
    final token = await _getValidToken();

    try {
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
          'Authorization': 'Bearer $token',
        },
        body: jsonEncode(treinoData),
      );
      return response;
    } catch (e) {
      throw Exception('Erro ao enviar treino: $e');
    }
  }

  Future<List<dynamic>> getTreinos() async {
    final token = await _getValidToken();
    final response = await http.get(
      Uri.parse(apiUrl),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      return json.decode(response.body) as List<dynamic>;
    } else {
      throw Exception('Falha ao carregar treinos: ${response.statusCode}');
    }
  }
}
