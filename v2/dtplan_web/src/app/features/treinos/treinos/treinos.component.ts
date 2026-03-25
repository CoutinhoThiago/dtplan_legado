import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { SidebarComponent } from '../../../shared/components/sidebar/sidebar.component';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/api/auth.service';
import { UsuarioService } from '../../../core/services/api/usuario.service';
import { TreinoService } from '../../../core/services/api/treino.service';
import { TreinoDTO } from '../../../core/models/treino.dto';
import { UsuarioDTO } from '../../../core/models/usuario.dto';

@Component({
  selector: 'app-treinos',
  standalone: true,
  imports: [CommonModule, SidebarComponent, FormsModule, HeaderComponent],
  templateUrl: './treinos.component.html',
  styleUrls: ['./treinos.component.css']
})
export class TreinosComponent implements OnInit {
  private authService = inject(AuthService);
  private usuarioService = inject(UsuarioService);
  private treinoService = inject(TreinoService);
  private router = inject(Router);
  
  mostrarApenasMeusTreinos: boolean = false; // Toggle para treinadores
  treinos: TreinoDTO[] = []; // Lista completa de treinos
  treinosFiltrados: TreinoDTO[] = []; // Lista de treinos filtrados
  filtroSelecionado: string = ''; // Filtro padrão
  usuario!: UsuarioDTO;

  ngOnInit() {
    this.carregarDadosUsuario();
    this.carregarTreinos();
  }

  carregarDadosUsuario() {
    const token = localStorage.getItem('token');

    if (token) {
      this.usuarioService.consultarUsuario(token).subscribe({
        next: (usuario) => {
          this.usuario = usuario;
          console.log('Usuário carregado:', this.usuario);
          this.aplicarFiltro(); // Aplica o filtro após carregar o usuário
        },
        error: (error) => {
          console.error('Erro ao carregar usuário:', error);
        }
      });
    } else {
      console.warn('Nenhum token encontrado, carregando do localStorage...');
    }
  }

  carregarTreinos() {
    const token = localStorage.getItem('token');

    if (token) {
      this.treinoService.getTreinos(token).subscribe({
        next: (treinos) => {
          this.treinos = treinos;
          console.log('Treinos carregados:', this.treinos);
          this.aplicarFiltro(); // Aplica o filtro após carregar os treinos
        },
        error: (error) => {
          console.error('Erro ao carregar treinos:', error);
        }
      });
    } else {
      console.warn('Nenhum token encontrado, carregando do localStorage...');
      this.treinos = this.treinoService.getTreinosSalvos(); // Busca do localStorage se não houver token
      this.aplicarFiltro(); // Aplica o filtro após carregar os treinos do localStorage
    }
  }

  aplicarFiltro() {
    if (!this.usuario || !this.treinos) {
      console.warn('Dados do usuário ou treinos não carregados.');
      return;
    }

    if (this.mostrarApenasMeusTreinos) {
      // Filtra apenas os treinos criados pelo usuário (autor)
      this.treinosFiltrados = this.treinos.filter(
        treino => treino.autor.id === this.usuario.id
      );
    } else {
      // Exibe todos os treinos do usuário
      this.treinosFiltrados = this.treinos.filter(
        treino => treino.usuario.id === this.usuario.id || treino.autor.id === this.usuario.id
      );
    }

    console.log('Treinos filtrados:', this.treinosFiltrados);
  }

  navigateTreino(treino: TreinoDTO) {
    console.log('Treino:', treino.id, ' salvo no local storage');
    localStorage.setItem(`treino${treino.id}`, JSON.stringify(treino));

    console.log('Navegando para treino:', treino.id);
    this.router.navigate(['/treino/detalhar/', treino.id]);
  }

  criarTreino() {
    console.log('Navegando para criar treino:');
    this.router.navigate(['/treino/criar']);
  }
}
