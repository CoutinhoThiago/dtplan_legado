import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  private router = inject(Router);

  // Navega para a rota especificada
  navigateTo(route: string) {
    this.router.navigate([route]);
  }
}