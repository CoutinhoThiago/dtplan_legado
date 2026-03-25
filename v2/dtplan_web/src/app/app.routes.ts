import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './core/auth/auth.guard';
import { CadastroComponent } from './pages/cadastro/cadastro.component';
import { ExecucoesTreinoComponent } from './features/treinos/execucoes-treino/execucoes-treino.component';
import { ExecutarTreinoComponent } from './features/treinos/executar-treino/executar-treino.component';
import { HomeComponent } from './features/home/home/home.component';
import { TreinosComponent } from './features/treinos/treinos/treinos.component';
import { TreinoComponent } from './features/treinos/treino/treino.component';
import { EditarTreinoComponent } from './features/treinos/editar-treino/editar-treino.component';
import { DietasComponent } from './features/dieta/dietas/dietas.component';
import { DietaComponent } from './features/dieta/dieta/dieta.component';
import { PerfilComponent } from './features/usuario/perfil/perfil.component';
import { EditarPerfilComponent } from './features/usuario/editar-perfil/editar-perfil.component';
import { ConfiguracoesComponent } from './features/configuracoes/configuracoes.component';
import { CriarTreinoComponent } from './features/treinos/criar-treino/criar-treino.component';
import { EditarFichaComponent } from './features/treinos/editar-ficha/editar-ficha.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'cadastro', component: CadastroComponent },

    //{ path: '**', redirectTo: 'home' },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
      
    { path: 'treinos', component: TreinosComponent, canActivate: [AuthGuard] },
    { path: 'treino/detalhar/:id', component: TreinoComponent, canActivate: [AuthGuard] },
    { path: 'treino/editar/:id', component: EditarTreinoComponent, canActivate: [AuthGuard] },
    { path: 'treino/criar', component: CriarTreinoComponent , canActivate: [AuthGuard]  },
    { path: 'treino/:id', component: ExecutarTreinoComponent, canActivate: [AuthGuard]  },
    { path: 'treino/execucoes/:id', component: ExecucoesTreinoComponent, canActivate: [AuthGuard]  },

    { path: 'ficha/editar/:id', component: EditarFichaComponent, canActivate: [AuthGuard]  },

    { path: 'dietas', component: DietasComponent, canActivate: [AuthGuard] },
    { path: 'dieta/detalhar/:id', component: DietaComponent, canActivate: [AuthGuard] },

    { path: 'perfil', component: PerfilComponent },
    { path: 'perfil/editar', component: EditarPerfilComponent },

    { path: 'configuracoes', component: ConfiguracoesComponent },
  ];
