import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { AuthService } from '../../../core/services/api/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, SidebarComponent, HeaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
    private authService = inject(AuthService);
  



  submit() {
    this.authService.logout();
  }
}
