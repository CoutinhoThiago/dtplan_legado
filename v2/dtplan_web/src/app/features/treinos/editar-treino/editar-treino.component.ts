import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { TreinoService } from '../../../core/services/api/treino.service';
import { UsuarioService } from '../../../core/services/api/usuario.service';
import { EditarTreinoDTO } from '../../../core/models/treino.dto';
import { AlunoDTO, UsuarioDTO } from '../../../core/models/usuario.dto';

@Component({
  selector: 'app-editar-treino',
  imports: [
    ReactiveFormsModule, 
    CommonModule,
    HeaderComponent
  ],
  templateUrl: './editar-treino.component.html',
  styleUrl: './editar-treino.component.css'
})
export class EditarTreinoComponent {
  private usuarioService = inject(UsuarioService);
  private treinoService = inject(TreinoService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder)

  treinoForm!: FormGroup; // Define o FormGroup
  treino: EditarTreinoDTO = {
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
  treinoId!: string;
  usuario!: UsuarioDTO;
  isTreinador: boolean = false;
  
  selectedAlunos!: AlunoDTO | null;
  alunosDisponiveis: AlunoDTO[] = [];

  ngOnInit() {
    this.treinoId = this.route.snapshot.paramMap.get('id') ?? '';
    this.inicializarFormulario();
    this.carregarTreino(this.treinoId);
    this.carregarDadosUsuario();
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

  inicializarFormulario() {
    this.treinoForm = this.fb.group({
      nome: ['', [Validators.required]],
      descricao: [''],
      autor: ['', [Validators.required]],
      aluno: [[], [Validators.required]]
    });
  }

  carregarTreino(treinoId: string) {
    const token = localStorage.getItem('token');
    if (token) {
      this.treinoService.getTreino(token, parseInt(treinoId)).subscribe({
        next: (treino) => {
          this.treino = treino;
          this.treinoForm.patchValue({
            nome: this.treino.nome,
            descricao: this.treino.descricao,
            autor: this.treino.autor,
            usuario: this.treino.usuario
          });
        },
        error: (error) => {
          console.error('Erro ao carregar treino:', error);
        }
      });
    }
  }


  editar(treinoId: string) {
    const token = localStorage.getItem('token');
    const login = localStorage.getItem('login');

    if (token && treinoId && login && this.treinoForm.valid) {
      // Atualiza os dados do treino com o formulário
      this.treino.nome = this.treinoForm.value.nome;
      this.treino.descricao = this.treinoForm.value.descricao;
      this.treino.autor = this.treinoForm.value.autor;
      this.treino.usuario.email = login;

      // Envia os dados do treino para o backend
      this.treinoService.putTreino(token, parseInt(treinoId), this.treino).subscribe({
        next: (response) => {
          console.log('Treino editado com sucesso!', response);
          this.voltar(treinoId);        
        },
        error: (error) => {
          console.error('Erro ao editar treino:', error);
        }
      });
    } else {
      console.error('Erro ao editar treino');
    }
  }

  voltar(treinoId: string) {
    this.router.navigate([`/treino/detalhar/${treinoId}`]);
  }

  removeAluno(): void {
    this.selectedAlunos = null; // Limpa o aluno selecionado
    this.treinoForm.get('aluno')?.setValue(null); // Atualiza o valor do controle do formulário
  }

  isAlunoSelecionado(aluno: AlunoDTO): boolean {
    return this.selectedAlunos?.id === aluno.id;
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
}
