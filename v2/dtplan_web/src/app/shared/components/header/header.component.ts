import { Component, inject, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../core/services/api/auth.service';
import { BotaoVoltarComponent } from '../botoes/botao-voltar/botao-voltar.component';
import { FormsModule } from '@angular/forms';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  standalone: true,
  imports: [
      FormsModule,
      FontAwesomeModule,
      BotaoVoltarComponent
    ],
})
export class HeaderComponent {
  @Input() title: string = '';

  @Input() voltar: boolean = false;
  @Input() caminhoVoltar: string = '';

  faSignOutAlt  = faSignOutAlt  

  private authService = inject(AuthService);
  private router = inject(Router);
    private route = inject(ActivatedRoute);

  logout() {
    this.authService.logout();
  }

  //voltarTela(): void {
  //  this.router.navigate([this.caminhoVoltar]);
  //}
}