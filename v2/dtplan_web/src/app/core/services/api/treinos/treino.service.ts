import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { CadastroTreinoDTO, EditarTreinoDTO, TreinoDTO, TreinoResponse } from '../../../models/treino.dto';

@Injectable({ providedIn: 'root' })
export class TreinoService {
  private apiUrl = 'http://localhost:8081';
  private http = inject(HttpClient);

  getTreinos(token: string): Observable<TreinoDTO[]> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.get<TreinoResponse>(`${this.apiUrl}/treinos/listar`, { headers }).pipe(
      map(response => response.content),
      tap(treinos => localStorage.setItem('treinos', JSON.stringify(treinos))),
      catchError(this.handleError)
    );
  }

  getTreino(token: string, id: number): Observable<TreinoDTO> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.get<TreinoDTO>(`${this.apiUrl}/treinos/detalhar/${id}`, { headers });
  }

  postTreino(token: string, treinoDTO: CadastroTreinoDTO): Observable<CadastroTreinoDTO> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    const body = {
      nome: treinoDTO.nome,
      descricao: treinoDTO.descricao,
      autor: { login: treinoDTO.autor.email },
      usuario: { id: treinoDTO.usuario.id }
    };
    return this.http.post<CadastroTreinoDTO>(`${this.apiUrl}/treinos/criar`, body, { headers });
  }

  putTreino(token: string, id: number, treinoDTO: EditarTreinoDTO): Observable<EditarTreinoDTO> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.put<EditarTreinoDTO>(`${this.apiUrl}/treinos/editar/${id}`, treinoDTO, { headers });
  }

  deleteTreino(token: string, id: number): Observable<void> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.delete<void>(`${this.apiUrl}/treinos/excluir/${id}`, { headers }).pipe(
      tap(() => {
        const treinosSalvos = this.getTreinosSalvos().filter(treino => treino.id !== id);
        localStorage.setItem('treinos', JSON.stringify(treinosSalvos));
      })
    );
  }

  getTreinosSalvos(): TreinoDTO[] {
    const treinos = localStorage.getItem('treinos');
    return treinos ? JSON.parse(treinos) : [];
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => new Error('Erro ao realizar a requisição'));
  }
}