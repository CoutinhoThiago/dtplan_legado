import 'package:shared_preferences/shared_preferences.dart';

Future<void> saveAuthorName(String name) async {
  final prefs = await SharedPreferences.getInstance();
  await prefs.setString('authorName', name);
}

Future<String> getAuthorName() async {
  final prefs = await SharedPreferences.getInstance();
  return prefs.getString('authorName') ?? 'Nome Padrão';
}