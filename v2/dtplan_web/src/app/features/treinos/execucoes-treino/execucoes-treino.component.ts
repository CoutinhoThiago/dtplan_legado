import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { 
  faChartLine, faStopwatch, faDumbbell, faArrowLeft,
  faClock, faCalendar, faInbox, faFilter, faSort,
  faTrophy, faFire, faTimes, faWeightHanging, faLightbulb,
  faChevronDown,
  faChevronUp
} from '@fortawesome/free-solid-svg-icons';
import { TreinoService } from '../../../core/services/api/treino.service';
import { ListarExecucaoDTO } from '../../../core/models/execucao.dto';
import { FichaDTO } from '../../../core/models/ficha.dto';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FichaExercicioDTO } from '../../../core/models/fichaExercicio.dto';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { BotaoVoltarComponent } from '../../../shared/components/botoes/botao-voltar/botao-voltar.component';
import { FichaService } from '../../../core/services/api/treinos/ficha.service';

@Component({
  selector: 'app-execucoes-treino',
  templateUrl: './execucoes-treino.component.html',
  styleUrls: ['./execucoes-treino.component.css'],
  standalone: true,
  imports: [
    CommonModule, 
    FontAwesomeModule,
    ReactiveFormsModule,
    FormsModule,
    BotaoVoltarComponent,
    HeaderComponent
  ]
})
export class ExecucoesTreinoComponent implements OnInit {
  // Ícones
  faChartLine = faChartLine;
  faStopwatch = faStopwatch;
  faDumbbell = faDumbbell;
  faArrowLeft = faArrowLeft;
  faClock = faClock;
  faCalendar = faCalendar;
  faInbox = faInbox;
  faFilter = faFilter;
  faSort = faSort;
  faTrophy = faTrophy;
  faFire = faFire;
  faTimes = faTimes;
  faWeightHanging = faWeightHanging;
  faLightbulb = faLightbulb;
  faChevronDown = faChevronDown;
  faChevronUp = faChevronUp;
  
  fichaId!: number;
  ficha!: FichaDTO;
  execucoes: ListarExecucaoDTO[] = [];
  filteredExecucoes: ListarExecucaoDTO[] = [];
  isLoading = true;
  isFilterOpen = false;
  weightHistory: { [key: number]: any[] } = {};
  historicoAberto = false;
  evolucaoAberta = false;

  // Estatísticas
  tempoMedio: number = 0;
  mediaSeries: number = 0;
  frequencia: number = 0;
  totalExecucoes: number = 0;
  progressoMedio: number = 0;
  melhorExecucao: ListarExecucaoDTO | null = null;

  // Filtros
  dateFilter: string = 'all';
  sortOrder: string = 'recent';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private treinoService: TreinoService,
    private fichaService: FichaService
  ) {}

  ngOnInit() {
    this.fichaId = +this.route.snapshot.paramMap.get('id')!;
    this.carregarFicha(this.fichaId);
    this.carregarExecucoes();
  }

  carregarFicha(fichaId: number): void {
    const token = localStorage.getItem('token');
    if (!token) return;

    this.fichaService.getFicha(token, fichaId).subscribe({
      next: (ficha) => {
        this.ficha = ficha;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar treino:', error);
        this.isLoading = false;
      }
    });
  }

  getWeightHistory(exercicioId: number): any[] {
    return this.weightHistory[exercicioId] || [];
  }

  carregarExecucoes() {
    const token = localStorage.getItem('token');
    if (token) {
      this.fichaService.getFicha(token, this.fichaId).subscribe({
        next: (ficha) => {
          this.ficha = ficha;
          this.calculateWeightHistory();
          
          this.treinoService.getExecucoesPorFicha(token, this.fichaId).subscribe({
            next: (execucoes: ListarExecucaoDTO[]) => {
              this.execucoes = execucoes;
              this.applyFilters();
              this.calcularEstatisticas();
              this.isLoading = false;
              this.calculateWeightHistory();
            },
            error: (error) => {
              console.error('Erro ao carregar execuções:', error);
              this.isLoading = false;
            }
          });
        },
        error: (error) => {
          console.error('Erro ao carregar ficha:', error);
          this.isLoading = false;
        }
      });
    }
  }

  calcularEstatisticas() {
    if (this.execucoes.length === 0) return;

    this.totalExecucoes = this.execucoes.length;
    
    // Tempo médio
    const totalTempo = this.execucoes.reduce((sum, e) => sum + (e.tempoTotal || 0), 0);
    this.tempoMedio = totalTempo / this.execucoes.length;

    // Média de séries
    const totalSeries = this.execucoes.reduce((sum, e) => sum + this.calcularSeriesConcluidas(e), 0);
    this.mediaSeries = totalSeries / this.execucoes.length;

    // Progresso médio
    const totalProgresso = this.execucoes.reduce((sum, e) => sum + this.calcularProgresso(e), 0);
    this.progressoMedio = totalProgresso / this.execucoes.length;

    // Melhor execução (maior progresso)
    this.melhorExecucao = this.execucoes.reduce((best, current) => 
      this.calcularProgresso(current) > this.calcularProgresso(best) ? current : best
    );

    // Frequência (execuções por semana)
    if (this.execucoes.length > 1) {
      const primeiraData = new Date(Math.min(...this.execucoes.map(e => new Date(e.data).getTime())));
      const ultimaData = new Date(Math.max(...this.execucoes.map(e => new Date(e.data).getTime())));
      const semanas = (ultimaData.getTime() - primeiraData.getTime()) / (1000 * 60 * 60 * 24 * 7);
      this.frequencia = semanas > 0 ? this.execucoes.length / semanas : this.execucoes.length;
    }
  }

  formatarTempo(segundos: number): string {
    if (!segundos) return 'N/A';
    
    const mins = Math.floor(segundos / 60);
    const secs = segundos % 60;
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  }

  calcularSeriesConcluidas(execucao: ListarExecucaoDTO): number {
    return execucao.execucaoExercicios.reduce((total, ex) => total + ex.seriesRealizadas, 0);
  }

  calcularSeriesTotais(execucao: ListarExecucaoDTO): number {
    return execucao.execucaoExercicios.reduce((total, ex) => total + ex.seriesPrevistas, 0);
  }

  calcularProgresso(execucao: ListarExecucaoDTO): number {
    const concluidas = this.calcularSeriesConcluidas(execucao);
    const totais = this.calcularSeriesTotais(execucao);
    return totais > 0 ? (concluidas / totais) * 100 : 0;
  }

  toggleFilter() {
    this.isFilterOpen = !this.isFilterOpen;
  }

  aplicarFiltros() {
    this.applyFilters();
    this.isFilterOpen = false;
  }

  applyFilters() {
    let result = [...this.execucoes];
    
    // Filtro por data
    const now = new Date();
    switch (this.dateFilter) {
      case 'week':
        const oneWeekAgo = new Date();
        oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
        result = result.filter(e => new Date(e.data) >= oneWeekAgo);
        break;
      case 'month':
        const oneMonthAgo = new Date();
        oneMonthAgo.setMonth(oneMonthAgo.getMonth() - 1);
        result = result.filter(e => new Date(e.data) >= oneMonthAgo);
        break;
      case 'year':
        const oneYearAgo = new Date();
        oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);
        result = result.filter(e => new Date(e.data) >= oneYearAgo);
        break;
    }
    
    // Ordenação
    switch (this.sortOrder) {
      case 'recent':
        result.sort((a, b) => 
          new Date(b.data).getTime() - new Date(a.data).getTime());
        break;
      case 'oldest':
        result.sort((a, b) => 
          new Date(a.data).getTime() - new Date(b.data).getTime());
        break;
      case 'duration':
        result.sort((a, b) => (b.tempoTotal || 0) - (a.tempoTotal || 0));
        break;
      case 'progress':
        result.sort((a, b) => 
          this.calcularProgresso(b) - this.calcularProgresso(a));
        break;
    }
    
    this.filteredExecucoes = result;
    this.calcularEstatisticas();
  }

  detalharExecucao(execucaoId: number) {
    this.router.navigate(['/execucao/detalhar', execucaoId]);
  }

  voltar() {
    if (this.ficha?.treinoId) {
      this.router.navigate(['/treino/detalhar', this.ficha.treinoId]);
    }
  }

  getExerciseProgress(exercicioExecutado: any): number {
    if (!this.ficha?.fichaExercicios) return 0;
    
    const exercicioFicha = this.ficha.fichaExercicios.find(
      (fe: FichaExercicioDTO) => fe.exercicio.id === exercicioExecutado.exercicioId
    );
    
    return exercicioFicha?.series 
      ? (exercicioExecutado.seriesRealizadas / exercicioFicha.series) * 100
      : 0;
  }

  getSeriesPrevistas(exercicioExecutado: any): number {
    if (!this.ficha?.fichaExercicios) return 0;
    
    const exercicioFicha = this.ficha.fichaExercicios.find(
      (fe: FichaExercicioDTO) => fe.exercicio.id === exercicioExecutado.exercicioId
    );
    
    return exercicioFicha?.series || 0;
  }

  calculateWeightHistory() {
    this.weightHistory = {};
    
    if (!this.ficha?.fichaExercicios || !this.execucoes) return;

    this.ficha.fichaExercicios.forEach((fe: FichaExercicioDTO) => {
      const exercicioId = fe.exercicio.id;
      this.weightHistory[exercicioId] = [];

      if (fe.carga) {
      this.weightHistory[exercicioId].push({
        data: this.ficha.dataCriacao, // Use a data de criação da ficha
        peso: fe.carga,
        variation: 0
      });
    }
      
      let lastWeight = fe.carga || 0;
      
      this.execucoes
        .slice()
        .sort((a, b) => new Date(a.data).getTime() - new Date(b.data).getTime())
        .forEach(execucao => {
          const execExercicio = execucao.execucaoExercicios.find(
            ee => ee.exercicioId === exercicioId
          );
          
          if (execExercicio?.carga !== null && execExercicio?.carga !== undefined) {
            const variation = execExercicio.carga - lastWeight;
            
            this.weightHistory[exercicioId].push({
              data: execucao.data,
              peso: execExercicio.carga,
              variation: variation
            });
            
            lastWeight = execExercicio.carga;
          }
        });
    });
  }

  calcularIntensidade(execucao: ListarExecucaoDTO): number {
    return 75; // Implemente sua lógica real aqui
  }

  calcularCargaTotal(execucao: ListarExecucaoDTO): number {
    return execucao.execucaoExercicios.reduce(
      (total, ex) => total + (ex.carga * ex.seriesRealizadas * ex.repeticoesRealizadas), 
      0
    );
  }

  fecharExecucao(execucaoId: number, event: MouseEvent) {
    event.stopPropagation();
    console.log('Fechar execução', execucaoId);
    // Implemente a lógica para fechar a execução
  }

  getWeightVariation(ex: any): number {
    if (!this.ficha?.fichaExercicios) return 0;

    const exercicioFicha = this.ficha.fichaExercicios.find(
      (fe: FichaExercicioDTO) => fe.exercicio.id === ex.exercicioId
    );

    if (!exercicioFicha) return 0;

    return ex.carga - exercicioFicha.carga;
  }

  getCurrentWeight(exercicioId: number): number | null {
  const history = this.weightHistory[exercicioId];
  if (!history || history.length === 0) return null;
  
  return history[history.length - 1].peso;
}
  toggleHistorico() {
    this.historicoAberto = !this.historicoAberto;
  }

  toggleEvolucao() {
    this.evolucaoAberta = !this.evolucaoAberta;
  }
}