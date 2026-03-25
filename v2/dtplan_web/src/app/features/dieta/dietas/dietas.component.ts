import { Router } from '@angular/router';
import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { DietaTipoMapperService } from '../../../shared/constants/dieta-tipo-mapper.service';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { AuthService } from '../../../core/services/api/auth.service';
import { DietaDTO, DietaService } from '../../../core/services/api/dieta.service';

@Component({
  selector: 'app-dieta',
  imports: [CommonModule, SidebarComponent, HeaderComponent],
  templateUrl: './dietas.component.html',
  styleUrl: './dietas.component.css'
})
export class DietasComponent implements OnInit {
  private authService = inject(AuthService);
  private dietaService = inject(DietaService);
  private router = inject(Router);
  public dietasTipoMapper = inject(DietaTipoMapperService);

    
  dietas: DietaDTO[] = [];

  ngOnInit() {
    this.carregarDieta();
  }

  carregarDieta() {
    const token = localStorage.getItem('token');

    if (token) {
      this.dietaService.getDietas(token).subscribe({
        next: (dieta) => {
          this.dietas = dieta;
          console.log('Dietas carregadas:', this.dietas);
        },
        error: (error) => {
          console.error('Erro ao carregar dietas:', error);
        }
      });
    } else {
      console.warn('Nenhum token encontrado, carregando do localStorage...');
      this.dietas = this.dietaService.getDietaSalva(); // Busca do localStorage se não houver token
    }
  }
  
  logout() {
    this.authService.logout();
  }
  
  navigateDieta(dieta: DietaDTO) {
    //this.dietaService.setDieta(dieta);
    localStorage.setItem(`dieta${dieta.id}`, JSON.stringify(dieta));
    this.router.navigate(['/dieta/detalhar/', dieta.id])
  }

}
