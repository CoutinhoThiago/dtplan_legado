import { Component, inject, OnInit } from '@angular/core';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UsuarioGeneroMapperService } from '../../../shared/constants/usuario-genero--mapper.service';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { UsuarioService } from '../../../core/services/api/usuario.service';
import { AuthService } from '../../../core/services/api/auth.service';
import { UsuarioDTO } from '../../../core/models/usuario.dto';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [SidebarComponent, CommonModule, HeaderComponent],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);
  private usuarioService = inject(UsuarioService)
  public generoTipoMapper = inject(UsuarioGeneroMapperService)
  

  usuario!: UsuarioDTO;
  
  ngOnInit() {
    this.carregarDadosUsuario();
  }

  // Carrega os dados do usuário a partir do AuthService
  carregarDadosUsuario() {
    const token = localStorage.getItem('token');

    if (token) {
      this.usuarioService.consultarUsuario(token).subscribe({
        next: (usuario) => {
          this.usuario = usuario;
          console.log('usuario carregados:', this.usuario);
        },
        error: (error) => {
          console.error('Erro ao carregar treinos:', error);
        }
      });
    } else {
      console.warn('Nenhum token encontrado, carregando do localStorage...');
    }
  }

  // Método para fazer logout
  logout() {
    this.authService.logout();
  }

  editarPerfil() {
    //localStorage.setItem(`dieta${dieta.id}`, JSON.stringify(dieta));
    this.router.navigate(['/perfil/editar/']) //, usuario.id])
  }

  formatarDataParaExibicao(data: string | null): string {
    if (!data) return 'Não informado';  // Caso a data seja nula
  
    const dataObj = new Date(data);
    const dia = String(dataObj.getDate()).padStart(2, '0'); // Adiciona zero à esquerda, se necessário
    const mes = String(dataObj.getMonth() + 1).padStart(2, '0'); // Mes começa de 0, então soma 1
    const ano = dataObj.getFullYear();
  
    return `${dia}/${mes}/${ano}`; // Formato DD/MM/YYYY
  }
}