import { Component, inject, OnInit, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CadastrarFichaExercicioDTO } from '../../../../core/models/fichaExercicio.dto';
import { exerciciosDTO } from '../../../../core/models/exercicio.dto';
import { TreinoService } from '../../../../core/services/api/treino.service';

@Component({
  selector: 'app-criar-ficha-exercicio-modal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './criar-ficha-exercicio-modal.component.html',
  styleUrls: ['./criar-ficha-exercicio-modal.component.css']
})
export class CriarFichaExercicioModalComponent implements OnInit {
  public activeModal = inject(NgbActiveModal);
  public treinoService = inject(TreinoService);

  exercicios: exerciciosDTO[] = [];
  cadastrarFichaExercicio: CadastrarFichaExercicioDTO = {
    fichaId: 0, // Será definido ao abrir o modal
    exercicioId: 0, // Inicializado com valor padrão
    repeticoes: 0,
    series: 0,
    carga: 0,
    duracao_minutos: 0,
    intensidade: 0
  };

  selectedExercicio: exerciciosDTO | null = null;

  @Input() fichaId!: number; // Recebe o ID da ficha ao abrir o modal

  ngOnInit() {
    this.carregarExercicios();
    this.cadastrarFichaExercicio.fichaId = this.fichaId; // Define o fichaId
  }

  carregarExercicios() {
    const token = localStorage.getItem('token');
    if (token) {
      this.treinoService.getExeciciios(token).subscribe({
        next: (exercicios) => {
          this.exercicios = exercicios;
          console.log('Exercícios carregados:', exercicios);
        },
        error: (error) => {
          console.error('Erro ao carregar exercícios:', error);
        }
      });
    }
  }

  onSubmit() {
    const token = localStorage.getItem('token');
    if (token) {
      // Verifica se um exercício foi selecionado
      if (!this.selectedExercicio) {
        console.error('Nenhum exercício selecionado.');
        return;
      }

      // Envia o DTO para o backend
      this.treinoService.postFichaExercicio(token, this.cadastrarFichaExercicio).subscribe({
        next: () => {
          this.activeModal.close('Salvo com sucesso');
        },
        error: (error) => {
          console.error('Erro ao salvar exercício:', error);
        }
      });
    }
  }

  salvarCadastro() {
    this.onSubmit();
  }

  fecharModal() {
    this.activeModal.dismiss('Fechado sem salvar');
  }

  removeExercicio(): void {
    this.selectedExercicio = null;
  }

  isExercicioSelecionado(exercicio: exerciciosDTO): boolean {
    return this.selectedExercicio?.id === exercicio.id;
  }

  onExercicioSelect(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const selectedId = selectElement.value;

    if (selectedId) {
      this.selectedExercicio = this.exercicios.find(exercicio => exercicio.id.toString() === selectedId)!;
      this.cadastrarFichaExercicio.exercicioId = this.selectedExercicio.id; // Atualiza o exercicioId
    } else {
      this.selectedExercicio = null;
      this.cadastrarFichaExercicio.exercicioId = 0; // Reseta o exercicioId
    }
  }
}