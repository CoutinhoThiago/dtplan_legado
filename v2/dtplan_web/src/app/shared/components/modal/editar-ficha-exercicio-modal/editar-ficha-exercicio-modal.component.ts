
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Component, inject, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TreinoService } from '../../../../core/services/api/treino.service';
import { FichaExercicioDTO } from '../../../../core/models/fichaExercicio.dto';

@Component({
  imports: [
    FormsModule,
  ],
  selector: 'app-editar-ficha-exercicio-modal',
  templateUrl: './editar-ficha-exercicio-modal.component.html',
  styleUrls: ['./editar-ficha-exercicio-modal.component.css']
})

export class EditarFichaExercicioModalComponent implements OnInit{
  @Input() fichaExercicio!: FichaExercicioDTO;

  public activeModal = inject(NgbActiveModal)
  public treinoService = inject(TreinoService)

  ngOnInit(): void {
    this.carrefarExercicio()
  }

  carrefarExercicio() {
    console.log('FichaExercicio recebido');
    console.log(this.fichaExercicio)
  }

  onSubmit() {
    const token = localStorage.getItem('token');
    if(token) {
      this.treinoService.putFichaExercicio(token, this.fichaExercicio.id, this.fichaExercicio).subscribe({
        next: () => {
          console.log('FichaExercicio salvo com sucesso');
          this.activeModal.close(this.fichaExercicio);
        },
        error: (error) => {
          console.error('Erro ao editar fichaExercicio:', error);
        }
      });
    }
  }

  salvarEdicao() {
    this.onSubmit(); // Chama o método onSubmit para salvar e fechar o modal
  }

  fecharModal() {
    this.activeModal.dismiss('Fechado sem salvar'); // Fecha o modal sem salvar
  }

  trackByExercicioId(index: number, exercicio: FichaExercicioDTO): number {
  return exercicio.exercicio.id; // Ou use exercicio.id se existir
}
}