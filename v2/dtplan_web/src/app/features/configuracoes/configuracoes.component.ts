import { Component, inject } from '@angular/core';
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';
import { HeaderComponent } from '../../shared/components/header/header.component';
import { AuthService } from '../../core/services/api/auth.service';

@Component({
  selector: 'app-configuracoes',
  standalone: true,
  imports: [SidebarComponent, HeaderComponent],
  templateUrl: './configuracoes.component.html',
  styleUrl: './configuracoes.component.css'
})
export class ConfiguracoesComponent {

  authService = inject(AuthService)
  
  
    logout() {
      this.authService.logout();
    }
}
