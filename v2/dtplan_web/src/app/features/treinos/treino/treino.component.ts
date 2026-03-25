import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faTimes, faPlus, faPencilAlt, faChevronDown, faChevronUp, faChartLine, faClock, faDumbbell, faTrophy } from '@fortawesome/free-solid-svg-icons';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CdkDrag, CdkDropList, DragDropModule } from '@angular/cdk/drag-drop';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { EditarFichaExercicioModalComponent } from '../../../shared/components/modal/editar-ficha-exercicio-modal/editar-ficha-exercicio-modal.component';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { UsuarioService } from '../../../core/services/api/usuario.service';
import { EditarFichaDTO, FichaDTO } from '../../../core/models/ficha.dto';
import { FichaExercicioDTO } from '../../../core/models/fichaExercicio.dto';
import { TreinoService } from '../../../core/services/api/treino.service';
import { TreinoDTO } from '../../../core/models/treino.dto';
import { UsuarioDTO } from '../../../core/models/usuario.dto';
import { CriarFichaExercicioModalComponent } from '../../../shared/components/modal/criar-ficha-exercicio-modal/criar-ficha-exercicio-modal.component';
import { FichaService } from '../../../core/services/api/treinos/ficha.service';
import { EditarFichaModalComponent } from '../../../shared/components/modal/editar-ficha-modal/editar-ficha-modal.component';

@Component({
  selector: 'app-treino',
  standalone: true,
  imports: [
    FontAwesomeModule,
    CommonModule,
    CdkDrag,
    CdkDropList,
    HeaderComponent,
  ],
  templateUrl: './treino.component.html',
  styleUrls: ['./treino.component.css']
})

export class TreinoComponent implements OnInit {
  faTimes = faTimes;
  faPlus = faPlus;
  faPencilAlt = faPencilAlt;
  faChevronDown = faChevronDown;
  faChevronUp = faChevronUp;

  faChartLine = faChartLine;
  faClock = faClock;
  faDumbbell = faDumbbell;
  faTrophy = faTrophy;

  ultimaExecucao: any;
  totalExecucoes: number = 0;
  melhorTempo: number = 0;

  private treinoService = inject(TreinoService);
  private fichaService = inject(FichaService);
  private usuarioService = inject(UsuarioService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private modalService = inject(NgbModal);
  private fb = inject(FormBuilder);

  treino!: TreinoDTO;
  fichas: FichaDTO[] = [];
  usuario!: UsuarioDTO;
  isAutor: boolean = false;
  isLoading: boolean = true;
  isOrderChanged: boolean = false;

  dadosFicha: EditarFichaDTO = {
    nome: '',
    treinoId: 0,
    fichaExercicios: [],
  };

  fichaForm: FormGroup;

  constructor() {
    this.fichaForm = this.fb.group({
      nome: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const treinoId = this.route.snapshot.paramMap.get('id');
    if (treinoId) {
      this.carregarDadosUsuario();
      this.carregarTreino(treinoId);
    } else {
      console.error('ID do treino não encontrado.');
      this.isLoading = false;
    }
  }

  carregarDadosUsuario(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      console.warn('Nenhum token encontrado.');
      this.isLoading = false;
      return;
    }

    this.usuarioService.consultarUsuario(token).subscribe({
      next: (usuario) => {
        this.usuario = usuario;
        this.verificarCarregamento();
      },
      error: () => {
        console.error('Erro ao carregar os dados do usuário.');
        this.isLoading = false;
      }
    });
  }

  carregarTreino(treinoId: string): void {
    const token = localStorage.getItem('token');
    if (!token) return;

    this.treinoService.getTreino(token, parseInt(treinoId)).subscribe({
      next: (treino) => {
        this.treino = treino;
        this.fichas = treino.fichas.map(ficha => ({ ...ficha, isExpanded: false }));
        this.isAutor = this.usuario?.email === treino.autor.email;
        
        // Carrega estatísticas do histórico
        this.carregarEstatisticas(treinoId);
        
        this.verificarCarregamento();
      },
      error: (error) => {
        console.error('Erro ao carregar treino:', error);
        this.isLoading = false;
      }
    });
  }

  carregarEstatisticas(treinoId: string): void {
    const token = localStorage.getItem('token');
    if (!token) return;

    this.treinoService.getEstatisticasTreino(token, parseInt(treinoId)).subscribe({
      next: (estatisticas) => {
        this.ultimaExecucao = estatisticas.ultimaExecucao;
        this.totalExecucoes = estatisticas.totalExecucoes;
      },
      error: (error) => {
        console.error('Erro ao carregar estatísticas:', error);
      }
    });
  }

  verHistorico(id: number): void {
    this.router.navigate(['/treino/execucoes/', id]);
  }

  verificarCarregamento(): void {
    if (this.usuario && this.treino) {
      this.isLoading = false;
    }
  }

  toggleExercicios(ficha: FichaDTO): void {
    ficha.isExpanded = !ficha.isExpanded;
  }

  voltar(): void {
    this.router.navigate(['/treinos']);
  }

  excluir(): void {
    const token = localStorage.getItem('token');
    const treinoId = this.route.snapshot.paramMap.get('id');

    if (token && treinoId) {
      this.treinoService.deleteTreino(token, parseInt(treinoId)).subscribe({
        next: () => {
          console.log('Treino excluído com sucesso.');
          this.router.navigate(['/treinos']);
        },
        error: (error) => {
          console.error('Erro ao excluir treino:', error);
        }
      });
    } else {
      console.error('Token ou ID do treino não encontrado.');
    }
  }

  editarTreino(id: number): void {
    this.router.navigate(['/treino/editar', id]);
  }

  adicionarFicha(): void {
    const token = localStorage.getItem('token');
    const treinoId = this.route.snapshot.paramMap.get('id');

    if (token && treinoId) {
      this.fichaService.postFicha(token, Number(treinoId)).subscribe({
        next: () => {
          console.log('Ficha adicionada com sucesso.');
          this.carregarTreino(treinoId);
        },
        error: (error) => {
          console.error('Erro ao cadastrar ficha:', error);
        }
      });
    }
  }

  excluirFicha(fichaId: number): void {
    const token = localStorage.getItem('token');
    const treinoId = this.route.snapshot.paramMap.get('id');

    if (token && treinoId) {
      this.fichaService.deleteFicha(token, fichaId).subscribe({
        next: () => {
          console.log('Ficha excluída com sucesso.');
          this.carregarTreino(treinoId);
        },
        error: () => {
          console.error('Erro ao excluir ficha:');
        }
      });
    }
  }

  editarFicha(ficha: FichaDTO) {
    //this.router.navigate([`ficha/editar/${ficha.id}`]);

    const modalRef = this.modalService.open(EditarFichaModalComponent);
    modalRef.componentInstance.fichaId = ficha.id;
    modalRef.componentInstance.ficha = ficha;
  }

  adicionarExercicio(ficha: FichaDTO): void {
    const modalRef: NgbModalRef = this.modalService.open(CriarFichaExercicioModalComponent);
    modalRef.componentInstance.fichaId = ficha.id;

    modalRef.result.then(
      (result) => {
        if (result === 'Salvo com sucesso') {
          console.log('Exercício adicionado com sucesso!');
          this.carregarTreino(ficha.treinoId.toString());
        }
      },
      (reason) => {
        console.log('Modal fechado sem salvar:', reason);
      }
    );
  }

  editarFichaExercicio(ficha: FichaDTO, exercicioId: number) {
    console.log('Dados recebidos pela função "editarFichaExercicio" da ficha')
    console.log('id da ficha: ' + exercicioId)
    console.log('Dados da ficha: /n' + ficha)

    const modalRef = this.modalService.open(EditarFichaExercicioModalComponent);
    
    const exercicio = ficha.fichaExercicios.find(e => e.exercicio.id === exercicioId);
    
    if (exercicio) {
      modalRef.componentInstance.fichaExercicio = { ...exercicio };
    } else {
      console.error('Exercício não encontrado com ID:', exercicioId);
    }

    // Recebe a resposta do PUT após fechar o modal
    modalRef.result.then(
    (resposta: any) => {
      if (resposta) {
        // Atualiza apenas os campos modificados mantendo o objeto exercicio
        const index = ficha.fichaExercicios.findIndex(
          e => e.id === resposta.id
        );
        
        if (index !== -1) {
          ficha.fichaExercicios[index] = {
            ...ficha.fichaExercicios[index], // Mantém os dados existentes
            ...resposta, // Atualiza com os novos valores
            exercicio: { // Mantém o objeto exercicio completo
              ...ficha.fichaExercicios[index].exercicio
            }
          };
        }
      }
    },
    (reason) => {
      console.log('Modal fechado sem salvar:', reason);
    }
  );
  }

  excluirFichaExercicio(ficha: FichaDTO, fichaExercicioId: number): void {
    const token = localStorage.getItem('token');
    const treinoId = this.route.snapshot.paramMap.get('id');

    if (token && treinoId) {
      this.treinoService.deleteFichaExercicio(token, fichaExercicioId).subscribe({
        next: () => {
          this.carregarTreino(treinoId);
          console.log('FichaExercicio excluída com sucesso.');
        },
        error: () => {
          console.error('Erro ao excluir ficha:');
        }
      });
    }
  }

  drop(event: CdkDragDrop<FichaExercicioDTO[]>, ficha: FichaDTO) {
    moveItemInArray(ficha.fichaExercicios, event.previousIndex, event.currentIndex);

    this.dadosFicha.fichaExercicios = ficha.fichaExercicios.map((exercicio, index) => ({
      id: exercicio.id,
      fichaExercicioId: exercicio.id,
      fichaId: ficha.id,
      exercicioId: exercicio.exercicio.id,
      ordem: index + 1,
      repeticoes: exercicio.repeticoes,
      series: exercicio.series,
      carga: exercicio.carga,
      duracao_minutos: exercicio.duracao_minutos,
      intensidade: exercicio.intensidade
    }));

    this.isOrderChanged = true;
  }

  salvarOrdem(ficha: FichaDTO) {
    this.atualizarOrdemExercicios(ficha);
    this.isOrderChanged = false;
  }
  
  atualizarOrdemExercicios(ficha: FichaDTO) {
    const exerciciosAtualizados = ficha.fichaExercicios.map((exercicio, index) => ({
      id: exercicio.id,
      fichaId: ficha.id, // Adiciona o fichaId
      exercicioId: exercicio.exercicio.id, // Adiciona o exercicioId
      ordem: index + 1,
      repeticoes: exercicio.repeticoes,
      series: exercicio.series,
      carga: exercicio.carga,
      duracao_minutos: exercicio.duracao_minutos,
      intensidade: exercicio.intensidade
    }));
  
    this.dadosFicha.nome = ficha.nome;
    this.dadosFicha.treinoId = ficha.treinoId;
    this.dadosFicha.fichaExercicios = exerciciosAtualizados;
  
    const token = localStorage.getItem('token');
    if (token) {
      this.fichaService.putFicha(token, ficha.id, this.dadosFicha).subscribe({
        next: () => {
          console.log('Ordem dos exercícios atualizada com sucesso.');
        },
        error: (error) => {
          console.error('Erro ao atualizar a ordem dos exercícios:', error);
        }
      });
    }
  }

  editar(fichaId: string) {
    const token = localStorage.getItem('token');
    if (token && this.fichaForm.valid) {
      this.dadosFicha.nome = this.fichaForm.value.nome;

      this.fichaService.putFicha(token, parseInt(fichaId), this.dadosFicha).subscribe({
        next: () => {
          console.log('Ficha atualizada com sucesso!');
          this.voltar();
        },
        error: () => console.error('Erro ao atualizar ficha.')
      });
    }
  }

  iniciarTreino(id: number) {
  console.log("Iniciando treino ID:", id);
    this.router.navigate(['/treino', id]);
}

}