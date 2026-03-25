import { Component, inject, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EditarFichaDTO, FichaDTO } from '../../../../core/models/ficha.dto';
import { FichaService } from '../../../../core/services/api/treinos/ficha.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-criar-ficha-modal',
  imports: [
      FormsModule,
    ],
  templateUrl: './criar-ficha-modal.component.html',
  styleUrl: './criar-ficha-modal.component.css'
})
export class CriarFichaModalComponent implements OnInit{
  
  @Input() fichaId!: number;
  @Input() ficha!: EditarFichaDTO;

  public activeModal = inject(NgbActiveModal)
  public fichaService = inject(FichaService)

  ngOnInit(): void {
    console.log('Ficha recebida');
  }

  

  onSubmit() {
    
  }

  salvarCadastro() {
    this.onSubmit(); // Chama o método onSubmit para salvar e fechar o modal
  }
  
  fecharModal() {
    this.activeModal.dismiss('Fechado sem salvar'); // Fecha o modal sem salvar
  }
  
  trackByExercicioId(index: number, exercicio: FichaDTO): number {
    return exercicio.id; // Ou use exercicio.id se existir
  }
}

