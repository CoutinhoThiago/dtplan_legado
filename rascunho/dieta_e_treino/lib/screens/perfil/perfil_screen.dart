import 'dart:io';
import 'package:flutter/material.dart';
import '../../services/token_servicce.dart';
import 'edit_profile_screen.dart';
import '../../models/usuario.dart';
import 'login_screen.dart';

class PerfilScreen extends StatefulWidget {
  final ValueNotifier<Usuario> usuarioNotifier;

  PerfilScreen({required this.usuarioNotifier});

  @override
  _PerfilScreenState createState() => _PerfilScreenState();
}

class _PerfilScreenState extends State<PerfilScreen> {
  final TokenService _tokenStorage = TokenService();
  bool isLoggedIn = false;

  @override
  void initState() {
    super.initState();
    widget.usuarioNotifier.addListener(_onUsuarioUpdated);
    _checkTokenValidity();
  }

  void _onUsuarioUpdated() {
    if (mounted) {
      setState(() {});
    }
  }

  void _checkTokenValidity() async {
    String? token = await _tokenStorage.getToken();
    if (token == null || token.isEmpty) {
      if (mounted) {
        setState(() {
          isLoggedIn = false;
        });
      }
    } else {
      if (mounted) {
        setState(() {
          isLoggedIn = true;
        });
      }
    }
  }

  @override
  void dispose() {
    widget.usuarioNotifier.removeListener(_onUsuarioUpdated);
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blueGrey[800],
        leading: Icon(
          Icons.person,
          color: Colors.amber[900],
        ),
        title: Text(
          "Perfil",
          style: TextStyle(
            color: Colors.amber[900],
          ),
        ),
      ),
      body: isLoggedIn ? _buildUserProfile() : _buildLoginPrompt(),
    );
  }

  Widget _buildUserProfile() {
    Usuario usuario = widget.usuarioNotifier.value;

    return SingleChildScrollView(
      padding: EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          Container(
            height: 150,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              color: Colors.grey,
            ),
            child: CircleAvatar(
              radius: 70,
              backgroundImage: usuario.imagePath != null
                  ? FileImage(File(usuario.imagePath!))
                  : null,
              child: usuario.imagePath == null
                  ? Icon(Icons.person, size: 100, color: Colors.white)
                  : null,
            ),
          ),
          SizedBox(height: 20),
          _buildUserInfo(usuario),
          SizedBox(height: 20),
          Center(
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                padding: EdgeInsets.symmetric(horizontal: 30, vertical: 15),
              ),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => EditProfileScreen(usuarioNotifier: widget.usuarioNotifier),
                  ),
                );
              },
              child: Text('Editar Perfil', style: TextStyle(fontSize: 18)),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildUserInfo(Usuario usuario) {
    return Card(
      child: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            ListTile(
              leading: Icon(Icons.person),
              title: Text('Nome'),
              subtitle: Text(usuario.nome),
            ),
            ListTile(
              leading: Icon(Icons.cake),
              title: Text('Idade'),
              subtitle: Text('${usuario.idade} anos'),
            ),
            ListTile(
              leading: Icon(Icons.height),
              title: Text('Altura'),
              subtitle: Text('${usuario.altura} m'),
            ),
            ListTile(
              leading: Icon(Icons.monitor_weight),
              title: Text('Peso'),
              subtitle: Text('${usuario.peso} kg'),
            ),
            ListTile(
              leading: Icon(Icons.directions_run),
              title: Text('Nível de Atividade'),
              subtitle: Text(usuario.nivelAtividade),
            ),
            ListTile(
              leading: Icon(Icons.flag),
              title: Text('Objetivo'),
              subtitle: Text(usuario.objetivo),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildLoginPrompt() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Text('Faça login para ver o perfil'),
          ElevatedButton(
            onPressed: () {
              Navigator.pushReplacement(context, MaterialPageRoute(builder: (_) => LoginScreen()));
            },
            child: Text('Login'),
          ),
        ],
      ),
    );
  }
}
