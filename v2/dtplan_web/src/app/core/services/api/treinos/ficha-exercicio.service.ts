import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { TreinoService } from './treino.service';
import { CadastrarFichaExercicioDTO, FichaExercicioDTO } from '../treino.service';

@Injectable({ providedIn: 'root' })
export class FichaExercicioService {
  private apiUrl = 'http://localhost:8081';
  private http = inject(HttpClient);
  private treinoService = inject(TreinoService);

  postFichaExercicio(token: string, fichaExercicio: CadastrarFichaExercicioDTO): Observable<FichaExercicioDTO> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.post<FichaExercicioDTO>(`${this.apiUrl}/fichaExercicio/criar`, fichaExercicio, { headers });
  }

  putFichaExercicio(token: string, fichaExercicio: FichaExercicioDTO): Observable<FichaExercicioDTO> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.put<FichaExercicioDTO>(
      `${this.apiUrl}/fichaExercicio/editar/${fichaExercicio.id}`, 
      fichaExercicio, 
      { headers }
    ).pipe(
      tap(() => {
        const treinosSalvos = this.treinoService.getTreinosSalvos()
          .filter(treino => treino.id !== fichaExercicio.id);
        localStorage.setItem('treinos', JSON.stringify(treinosSalvos));
      })
    );
  }

  deleteFichaExercicio(token: string, id: number): Observable<void> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.delete<void>(`${this.apiUrl}/fichaExercicio/excluir/${id}`, { headers }).pipe(
      tap(() => {
        const treinosSalvos = this.treinoService.getTreinosSalvos().filter(treino => treino.id !== id);
        localStorage.setItem('treinos', JSON.stringify(treinosSalvos));
      })
    );
  }

  reordenarFichaExercicios(token: string, exercicios: FichaExercicioDTO[]): Observable<void> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.put<void>(`${this.apiUrl}/fichaExercicio/reordenar`, exercicios, { headers });
  }
}