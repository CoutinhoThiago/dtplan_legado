import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { TreinoService } from './treino.service';
import { CadastrarFichaDTO, EditarFichaDTO, FichaDTO } from '../../../models/ficha.dto';

@Injectable({ 
  providedIn: 'root' 
})
export class FichaService {
  private apiUrl = 'http://localhost:8081';

  private http = inject(HttpClient);
  private treinoService = inject(TreinoService);

  // Cria uma nova ficha
    postFicha(token: string, treinoId: number): Observable<CadastrarFichaDTO> {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
  
      const body = {
        nome: "novaFicha",
        treino: treinoId
      };
  
      return this.http.post<CadastrarFichaDTO>(this.apiUrl + '/fichas/criar', body, { headers }).pipe(
        tap((ficha) => {
          console.log('Ficha cadastrada:', ficha);
  
          // Atualiza os treinos no localStorage após criar a ficha
          this.treinoService.getTreinos(token).subscribe((treinos) => {
            localStorage.setItem('treinos', JSON.stringify(treinos));
            console.log('Treinos atualizados no localStorage:', treinos);
          });
        }),
        catchError((error: HttpErrorResponse) => {
          console.error('Erro ao cadastrar ficha:', error);
          return throwError(() => new Error('Erro ao cadastrar ficha. Tente novamente mais tarde.'));
        })
      );
    }
  
    getFicha(token: string, id: number): Observable<FichaDTO> {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
      });
  
      return this.http.get<FichaDTO>(this.apiUrl + '/fichas/detalhar/' + id, { headers }).pipe(
        tap((ficha) => {
          console.log('Ficha recebida:', ficha);
        }),
        catchError(this.handleError)
      );
    }
    
    // Exclui uma ficha
    deleteFicha(token: string, id: number): Observable<void> {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
      });
  
      return this.http.delete<void>(`${this.apiUrl}/fichas/excluir/${id}`, { headers }).pipe(
        tap(() => {
          console.log('Ficha excluída com sucesso!');
  
          const treinosSalvos = this.treinoService.getTreinosSalvos().filter(treino => treino.id !== id);
          localStorage.setItem('treinos', JSON.stringify(treinosSalvos));
        }),
        catchError(this.handleError)
      );
    }
  

  putFicha(token: string, id: number, fichaDTO: EditarFichaDTO): Observable<EditarFichaDTO> {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`, // Envia o token no cabeçalho
        'Content-Type': 'application/json'
      });
      const body = {
        nome: fichaDTO.nome,
        fichaExercicios: []
      };
  
      return this.http.put<EditarFichaDTO>(`${this.apiUrl}/fichas/editar/${id}`, body, { headers }).pipe(
        tap((ficha) => {
          console.log('Ficha atualizado:', ficha);

          this.treinoService.getTreinos(token).subscribe((treinos) => {
            localStorage.setItem('treinos', JSON.stringify(treinos));
            console.log('Treinos atualizados no localStorage:', treinos);
          });
        }),
        catchError(this.handleError)
      );
    }

  // Manipula erros das requisições HTTP
  private handleError(error: HttpErrorResponse) {
    console.error('Erro na requisição:', error);
    return throwError(() => new Error('Erro ao realizar a requisição. Tente novamente mais tarde.'));
  }
}