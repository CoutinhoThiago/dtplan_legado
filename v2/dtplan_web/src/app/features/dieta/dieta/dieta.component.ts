import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faTimes, faPlus, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { AuthService } from '../../../core/services/api/auth.service';
import { DietaDTO, DietaService, RefeicaoDTO } from '../../../core/services/api/dieta.service';



@Component({
  selector: 'app-dieta',
  standalone: true,
  imports: [
    FontAwesomeModule,
    HeaderComponent
  ],
  templateUrl: './dieta.component.html',
  styleUrl: './dieta.component.css'
})
export class DietaComponent {
  // Ícones do Font Awesome
  faTimes = faTimes; // Ícone de "x" para remover
  faPlus = faPlus;   // Ícone de "+" para adicionar
  faPencilAlt = faPencilAlt; // Ícone de lápis para editar
  faChevronDown = faChevronDown; // Ícone de "chevron-down" para expandir
  faChevronUp = faChevronUp; // Ícone de "chevron-up" para recolher

  // Injeção de dependências
  private authService = inject(AuthService);
  private dietaService = inject(DietaService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  dieta!: DietaDTO;
  refeicoes: RefeicaoDTO[] = [];

  ngOnInit() {
    const dietaId = this.route.snapshot.paramMap.get('id');
    console.log('Dieta ID:', dietaId);

    if (dietaId) {
      this.carregarRefeicoes(dietaId);
    } else {
      console.error('ID do dieta não encontrado');
    }
  }

  // Alterna a visibilidade dos exercícios de uma refeicao
  toggleAlimentos(refeicao: RefeicaoDTO) {
    refeicao.isExpanded = !refeicao.isExpanded;
  }

  // Carrega as refeicoes do dieta
  carregarRefeicoes(dietaId: string) {
    const token = localStorage.getItem('token');

    if (token) {
      this.dietaService.getDieta(token, parseInt(dietaId)).subscribe({
        next: (dieta) => {
          this.dieta = dieta;
          this.refeicoes = dieta.refeicoes.map((refeicao) => {
            return { ...refeicao, isExpanded: false };
          })
          console.log('Dieta carregado:', dieta);
          console.log('refeicoes carregadas:', this.refeicoes);
        },
        error: (error) => {
          console.error('Erro ao carregar dietas:', error);
        }
      });
    } else {
      console.warn('Nenhum token encontrado, carregando do localStorage...');
    }
  }

  // Navega de volta para a página inicial
  voltar() {
    this.router.navigate(['/dietas']);
  }

  // Exclui o treino atual
  excluir() {
    const token = localStorage.getItem('token');
    const dietaId = this.route.snapshot.paramMap.get('id');
    console.log('Dieta ID:', dietaId);

    if (token && dietaId) {
      this.dietaService.deleteDieta(token, parseInt(dietaId)).subscribe({
        next: (response) => {
          console.log('Dieta excluído com sucesso!', response);
          this.router.navigate(['/dietas']);
        },
        error: (error) => {
          console.error('Erro ao excluir dieta:', error);
        }
      });
    } else {
      console.error('Erro ao excluir dieta');
    }
  }

    editar(id: number) {
      console.log('Navegando para criar dieta:');
      this.router.navigate(['/dieta/editar', id]);
    }


    adicionarExercicio(refeicaoId: number) {}
    editarExercicio(refeicaoId: number, alimentoId: number) {}
    excluirExercicio(refeicaoId: number, alimentoId: number) {}
    
}