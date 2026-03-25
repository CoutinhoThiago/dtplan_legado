import { Component, inject, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EditarFichaDTO, FichaDTO } from '../../../../core/models/ficha.dto';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FichaService } from '../../../../core/services/api/treinos/ficha.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-editar-ficha-modal',
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './editar-ficha-modal.component.html',
  styleUrls: ['./editar-ficha-modal.component.css']
})
export class EditarFichaModalComponent implements OnInit{
  
  @Input() fichaId!: number;
  @Input() ficha!: EditarFichaDTO;

  public activeModal = inject(NgbActiveModal)
  public fichaService = inject(FichaService)

  ngOnInit(): void {
    this.carregarFicha()
  }

  carregarFicha() {
    console.log('Ficha recebida');
    console.log(this.ficha)
  }

  onSubmit() {
    const token = localStorage.getItem('token')
    if(token) {
      this.fichaService.putFicha(token, this.fichaId, this.ficha).subscribe({
        next: () => {
          console.log('FichaExercicio salvo com sucesso');
          this.activeModal.close(this.ficha);
        },
        error: () => {
          console.error('Erro ao editar fichaExercicio:');
        }
      })
    }
  }

  salvarEdicao() {
    this.onSubmit(); // Chama o método onSubmit para salvar e fechar o modal
  }
  
  fecharModal() {
    this.activeModal.dismiss('Fechado sem salvar'); // Fecha o modal sem salvar
  }
  
  trackByExercicioId(index: number, exercicio: FichaDTO): number {
    return exercicio.id; // Ou use exercicio.id se existir
  }
}