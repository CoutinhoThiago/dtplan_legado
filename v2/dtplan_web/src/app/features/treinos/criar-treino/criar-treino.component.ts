import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { TreinoService } from '../../../core/services/api/treino.service';
import { UsuarioService } from '../../../core/services/api/usuario.service';
import { CadastroTreinoDTO } from '../../../core/models/treino.dto';
import { AlunoDTO, UsuarioDTO } from '../../../core/models/usuario.dto';

@Component({
  selector: 'app-criar-treino',
  standalone: true,
  imports: [
    ReactiveFormsModule, 
    CommonModule,
    HeaderComponent
  ],
  templateUrl: './criar-treino.component.html',
  styleUrls: ['./criar-treino.component.css']
})
export class CriarTreinoComponent  implements OnInit {
  private treinoService = inject(TreinoService);
  private usuarioService = inject(UsuarioService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  treinoForm!: FormGroup;
  treino: CadastroTreinoDTO = {
    nome: '',
    descricao: '',
    autor: {
      id: 0,
      email: '',
      senha: '',
      nome: '',
      cpf: '',
      tipoUsuario: '',
      crn: '',
      cref: '',
      alunos: [],
      dataNascimento: '',
      genero: '',
      altura: 0,
      pesoAtual: 0
    },
    usuario: {
      id: 0,
      email: '',
      senha: '',
      nome: '',
      cpf: '',
      tipoUsuario: '',
      crn: '',
      cref: '',
      alunos: [],
      dataNascimento: '',
      genero: '',
      altura: 0,
      pesoAtual: 0
    }
  };
  usuario!: UsuarioDTO;
  isTreinador: boolean = false;

  selectedAlunos!: AlunoDTO | null;
  alunosDisponiveis: AlunoDTO[] = [];

  constructor() {}

  ngOnInit(): void {
    this.inicializarFormulario();
    this.carregarDadosUsuario();
  }

  inicializarFormulario() {
    this.treinoForm = this.fb.group({
      nome: ['', [Validators.required]],
      descricao: [''],
      autor: [''],
      aluno: [[], [Validators.required]]
    });
  }

  carregarDadosUsuario() {
    const token = localStorage.getItem('token');
  
    if (token) {
      this.usuarioService.consultarUsuario(token).subscribe({
        next: (usuario) => {
          this.usuario = usuario;
          console.log('Usuário carregado:', this.usuario);
  
          // Verifica se o usuário é um treinador ou ambos
          if (usuario.tipoUsuario === 'ambos' || usuario.tipoUsuario === 'treinador') {
            this.isTreinador = true;
            this.alunosDisponiveis = usuario.alunos; // Carrega os alunos do treinador
          }
  
          // Se o usuário for um aluno ou ambos, adiciona ele mesmo à lista de alunos disponíveis
          if (usuario.tipoUsuario === 'ambos' || usuario.tipoUsuario === 'aluno') {
            const usuarioComoAluno: AlunoDTO = {
              id: usuario.id,
              nome: usuario.nome,
              cpf: usuario.cpf,
              dataNascimento: usuario.dataNascimento,
              altura: usuario.altura,
              pesoAtual: usuario.pesoAtual,
              nivelAtividade: '', // Adicione os campos necessários
              objetivo: '',
              tipoUsuario: usuario.tipoUsuario,
              cref: usuario.cref,
              crn: usuario.crn
            };
  
            // Adiciona o próprio usuário à lista de alunos disponíveis
            this.alunosDisponiveis.push(usuarioComoAluno);
          }
  
          // Se não for treinador, o aluno selecionado é o próprio usuário
          if (!this.isTreinador) {
            this.selectedAlunos = {
              id: usuario.id,
              nome: usuario.nome,
              cpf: usuario.cpf,
              dataNascimento: usuario.dataNascimento,
              altura: usuario.altura,
              pesoAtual: usuario.pesoAtual,
              nivelAtividade: '', // Adicione os campos necessários
              objetivo: '',
              tipoUsuario: usuario.tipoUsuario,
              cref: usuario.cref,
              crn: usuario.crn
            };
            this.treinoForm.get('aluno')?.setValue(this.selectedAlunos.id); // Define o ID do aluno selecionado
          }
        },
        error: () => {
          console.error('Erro ao carregar os dados do usuário:');
        }
      });
    } else {
      console.warn('Nenhum token encontrado, carregando do localStorage...');
    }
  }

  onAlunoSelect(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const selectedId = selectElement.value; // Pega o ID do aluno selecionado
  
    if (selectedId) {
      this.selectedAlunos = this.alunosDisponiveis.find(aluno => aluno.id.toString() === selectedId)!;
    } else {
      this.selectedAlunos = null!; // Limpa a seleção se nenhum aluno for selecionado
    }
  
    this.treinoForm.get('aluno')?.setValue(selectedId); // Atualiza o valor do controle do formulário
  }

  removeAluno(): void {
    this.selectedAlunos = null; // Limpa o aluno selecionado
    this.treinoForm.get('aluno')?.setValue(null); // Atualiza o valor do controle do formulário
  }

  isAlunoSelecionado(aluno: AlunoDTO): boolean {
    return this.selectedAlunos?.id === aluno.id;
  }

  onSubmit() {
    const token = localStorage.getItem('token');
    const login = localStorage.getItem('login');

    if (token && login && this.treinoForm.valid) {
      this.treino.nome = this.treinoForm.value.nome;
      this.treino.descricao = this.treinoForm.value.descricao;
      this.treino.autor.email = this.usuario.email;
      this.treino.usuario.email = this.usuario.email; // Define o email do usuário

      if (this.selectedAlunos) {
        this.treino.usuario.id = this.selectedAlunos.id; // Atribui o ID do aluno selecionado
      } else {
        console.error('Nenhum aluno selecionado.');
        return; // Interrompe a execução se nenhum aluno estiver selecionado
      }

      this.treinoService.postTreino(token, this.treino).subscribe({
        next: (response) => {
          console.log('Treino cadastrado com sucesso!', response);
          this.voltar();
        },
        error: (error) => {
          console.error('Erro ao cadastrar treino:', error);
        }
      });
    } else {
      console.error('Campos obrigatórios não preenchidos');
    }
  }

  voltar() {
    this.router.navigate(['/treinos']);
  }
}