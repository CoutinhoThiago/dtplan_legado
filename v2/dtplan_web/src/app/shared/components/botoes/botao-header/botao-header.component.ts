import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-voltar',
  templateUrl: './botao-header.component.html',
  styleUrls: ['./botao-header.component.css'],
  standalone: true,
  imports: [
    FontAwesomeModule,
    CommonModule
  ]
})
export class BotaoVoltarComponent {
  // Ícone
  faArrowLeft = faArrowLeft;
  
  // Inputs para personalização
  @Input() texto: string = 'btn.header';
  @Input() rota: string | null = null;
  @Input() backgroundColor: string = '#3498db';
  @Input() border: string = '#3498db';
  @Input() hoverColor: string = '#2980b9';
  @Input() tamanho: 'pequeno' | 'medio' | 'grande' = 'medio';
  
  // Output para eventos personalizados
  @Output() aoClicar = new EventEmitter<void>();

  constructor(private router: Router) {}

  voltar() {
    // Emite o evento personalizado
    this.aoClicar.emit();
    
    // Navega para a rota especificada ou volta na história do navegador
    if (this.rota) {
      this.router.navigate([this.rota]);
    } else {
      window.history.back();
    }
  }
}