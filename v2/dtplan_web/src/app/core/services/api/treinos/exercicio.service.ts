import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { ExercicioDTO, ExercicioResponse } from '../treino.service';

@Injectable({ providedIn: 'root' })
export class ExercicioService {
  private apiUrl = 'http://localhost:8081';
  private http = inject(HttpClient);

  getExercicios(token: string): Observable<ExercicioDTO[]> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.get<ExercicioResponse>(`${this.apiUrl}/exercicios/listar`, { headers }).pipe(
      map(response => response.content),
      tap(exercicios => localStorage.setItem('exercicios', JSON.stringify(exercicios))),
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    return throwError(() => new Error('Erro ao carregar exercícios'));
  }
}