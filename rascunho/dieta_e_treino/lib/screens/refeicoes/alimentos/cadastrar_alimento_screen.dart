import 'package:flutter/material.dart';

class CadastrarAlimentoScreen extends StatefulWidget {
  @override
  _CadastrarAlimentoScreenState createState() => _CadastrarAlimentoScreenState();
}

class _CadastrarAlimentoScreenState extends State<CadastrarAlimentoScreen> {
  final _formKey = GlobalKey<FormState>();
  bool _isButtonEnabled = false;

  final _nomeController = TextEditingController();
  final _caloriasController = TextEditingController();
  final _proteinasController = TextEditingController();
  final _carboidratosController = TextEditingController();
  final _gordurasController = TextEditingController();

  void _checkFormValid() {
    if (_nomeController.text.isNotEmpty &&
        _caloriasController.text.isNotEmpty &&
        _proteinasController.text.isNotEmpty &&
        _carboidratosController.text.isNotEmpty &&
        _gordurasController.text.isNotEmpty) {
      setState(() {
        _isButtonEnabled = true;
      });
    } else {
      setState(() {
        _isButtonEnabled = false;
      });
    }
  }

  void _salvarAlimento() {
    if (_formKey.currentState!.validate()) {
      Map<String, dynamic> novoAlimento = {
        'nome': _nomeController.text,
        'calorias': _caloriasController.text,
        'proteinas': _proteinasController.text,
        'carboidratos': _carboidratosController.text,
        'gorduras': _gordurasController.text,
      };

      Navigator.of(context).pop(novoAlimento);
    }
  }

  @override
  void initState() {
    super.initState();
    _nomeController.addListener(_checkFormValid);
    _caloriasController.addListener(_checkFormValid);
    _proteinasController.addListener(_checkFormValid);
    _carboidratosController.addListener(_checkFormValid);
    _gordurasController.addListener(_checkFormValid);
  }

  @override
  void dispose() {
    _nomeController.dispose();
    _caloriasController.dispose();
    _proteinasController.dispose();
    _carboidratosController.dispose();
    _gordurasController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Cadastrar Alimento'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: <Widget>[
              TextFormField(
                controller: _nomeController,
                decoration: InputDecoration(labelText: 'Nome do Alimento'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira o nome do alimento.';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _caloriasController,
                decoration: InputDecoration(labelText: 'Calorias'),
                keyboardType: TextInputType.number,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira as calorias.';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _proteinasController,
                decoration: InputDecoration(labelText: 'Proteínas'),
                keyboardType: TextInputType.number,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira a quantidade de proteínas.';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _carboidratosController,
                decoration: InputDecoration(labelText: 'Carboidratos'),
                keyboardType: TextInputType.number,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira a quantidade de carboidratos.';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _gordurasController,
                decoration: InputDecoration(labelText: 'Gorduras'),
                keyboardType: TextInputType.number,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira a quantidade de gorduras.';
                  }
                  return null;
                },
              ),
              SizedBox(height: 20.0),
              ElevatedButton(
                onPressed: _isButtonEnabled ? _salvarAlimento : null,
                child: Text('Salvar Alimento'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
