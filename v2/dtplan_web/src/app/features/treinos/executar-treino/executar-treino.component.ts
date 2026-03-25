import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CadastrarExecucaoDTO } from '../../../core/models/execucao.dto';
import { TreinoService } from '../../../core/services/api/treino.service';
import { FichaService } from '../../../core/services/api/treinos/ficha.service';

@Component({
  selector: 'app-executar-treino',
  standalone: true,
  imports: [
    FontAwesomeModule,
    CommonModule,
    HeaderComponent,
    ReactiveFormsModule
  ],
  templateUrl: './executar-treino.component.html',
  styleUrls: ['./executar-treino.component.css']
})
export class ExecutarTreinoComponent implements OnInit, OnDestroy {
  private treinoService = inject(TreinoService);
  private fichaService = inject(FichaService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);

  fichaId!: string;
  ficha: any = null;
  exercicios: any[] = [];
  tempoDecorrido: number = 0;
  intervalo: any;
  exercicioAtualIndex: number = 0;
  exercicioForm!: FormGroup;
  isProcessing: boolean = false;

  ngOnInit() {
    this.fichaId = this.route.snapshot.paramMap.get('id') ?? '';
    this.initForm();
    this.carregarFicha(this.fichaId);
    this.iniciarCronometro();
  }

  initForm() {
    this.exercicioForm = this.fb.group({
      repeticoes: ['', [Validators.required, Validators.min(1)]],
      carga: ['', [Validators.min(0)]]
    });
  }

  carregarFicha(fichaId: string) {
    const token = localStorage.getItem('token');
    if (token) {
      this.fichaService.getFicha(token, parseInt(fichaId)).subscribe({
        next: (ficha) => {
          this.ficha = ficha;
          this.exercicios = ficha.fichaExercicios.map((ex: any) => ({
        ...ex,
        seriesConcluidas: 0
      }));
          this.updateFormWithCurrentExercise();
        },
        error: (error) => {
          console.error('Erro ao carregar treino:', error);
        }
      });
    }
  }

  updateFormWithCurrentExercise() {
    const currentExercise = this.exercicios[this.exercicioAtualIndex];
    if (currentExercise) {
      this.exercicioForm.patchValue({
        repeticoes: currentExercise.repeticoes || '',
        carga: currentExercise.carga || ''
      });
    }
  }

  onFormChange() {
    const currentExercise = this.exercicios[this.exercicioAtualIndex];
    if (currentExercise && this.exercicioForm.valid) {
      // Usar nomes corretos das propriedades
      currentExercise.repeticoes = this.exercicioForm.value.repeticoes;
      currentExercise.carga = this.exercicioForm.value.carga;
    }
  }

  iniciarCronometro() {
    this.intervalo = setInterval(() => {
      this.tempoDecorrido++;
    }, 1000);
  }

  async proximoExercicio() {
    if (this.exercicioAtualIndex < this.exercicios.length - 1) {
      this.isProcessing = true;
      await new Promise(resolve => setTimeout(resolve, 300)); // Simula processamento
      this.exercicioAtualIndex++;
      this.updateFormWithCurrentExercise();
      this.isProcessing = false;
    }
  }

  async exercicioAnterior() {
    if (this.exercicioAtualIndex > 0) {
      this.isProcessing = true;
      await new Promise(resolve => setTimeout(resolve, 300)); // Simula processamento
      this.exercicioAtualIndex--;
      this.updateFormWithCurrentExercise();
      this.isProcessing = false;
    }
  }

  async marcarSerieConcluida(exercicio: any) {
    if (!exercicio.seriesConcluidas) {
      exercicio.seriesConcluidas = 0;
    }
    if (exercicio.seriesConcluidas < exercicio.series) {
      this.isProcessing = true;
      await new Promise(resolve => setTimeout(resolve, 500)); // Simula processamento
      exercicio.seriesConcluidas++;
      this.isProcessing = false;
    }
  }

  finalizarTreino(treinoId: number) {
  clearInterval(this.intervalo);

  // Preparar os dados no formato correto
  const execucaoData: CadastrarExecucaoDTO = {
    treinoId: this.ficha.treino?.id || treinoId,
    fichaId: this.ficha.id,
    exercicios: this.exercicios.map(ex => ({
      // Propriedades obrigatórias:
      id: ex.exercicio.id,
      seriesRealizadas: ex.seriesConcluidas || 0, // Usar valor marcado
      repeticoes: ex.repeticoes, // Valor do formulário
      carga: ex.carga, // Valor do formulário
      exercicioId: ex.exercicioId,
      exercicioNome: ex.exercicioNome // Corrigido para usar nome
    })),
    observacao: `Treino concluído em ${this.tempoDecorrido} segundos`
  };

  const token = localStorage.getItem('token');
  if (token) {
    this.treinoService.cadastrarExecucao(token, execucaoData).subscribe({
      next: (response) => {
        console.log('Execução cadastrada com sucesso:', response);
        this.router.navigate([`/treino/detalhar/${treinoId}`]);
      },
      error: (error) => {
        console.error('Erro ao cadastrar execução:', error);
        // Tratar erro (ex: mostrar mensagem para o usuário)
      }
    });
  } else {
    console.error('Token não encontrado');
    this.router.navigate(['/login']);
  }
}

  cancelar(treinoId: number) {
    this.router.navigate([`/treino/detalhar/${treinoId}`]);
  }

  ngOnDestroy() {
    if (this.intervalo) {
      clearInterval(this.intervalo);
    }
  }
}