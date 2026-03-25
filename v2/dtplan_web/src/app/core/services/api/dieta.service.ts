import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, map, Observable, tap, throwError } from 'rxjs';

export interface DietaDTO {
  id: number;
  nome: string;
  descricao: string;
  tipo: string;
  autor: string;
  usuario: string;
  refeicoes: RefeicaoDTO[];
}

export interface RefeicaoDTO {
  id: number,
  nome: string;
  descricao: string;
  autor: string;
  usuario: string;
  isExpanded: boolean
  refeicaoAlimentos: refeicaoAlimentos[]
}

export interface refeicaoAlimentos {
  id: number,
  nome: string
  descicao: string
  quantidade: number
  alimento: AlimentoDTO
}

export interface AlimentoDTO {
  id: number,
  nome: string
  descicao: string
}

export interface DietaResponse {
  content: DietaDTO[]
}

@Injectable({
  providedIn: 'root'
})
export class DietaService {
  private apiUrl = 'http://localhost:8081'; // URL do backend
  
    constructor(private http: HttpClient, private router: Router) {}
  
    // Corrigido o tipo de retorno para Observable<TreinoResponse>
    getDietas(token: string): Observable<DietaDTO[]> {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
      });
  
      return this.http.get<DietaResponse>(this.apiUrl + '/dietas/listar', { headers }).pipe(
        // Usando `map` para extrair o `content` e retornar diretamente o array de TreinoDTO
        map((response) => response.content),
        tap((dietas) => {
          console.log('Dietas recebidos:', dietas);
          localStorage.setItem('dietas', JSON.stringify(dietas));
        }),
        catchError(this.handleError)
      );
    }

    getDieta(token: string, id:number): Observable<DietaDTO> {
        const headers = new HttpHeaders({
          'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
        });
    
        return this.http.get<DietaDTO>(this.apiUrl + '/dietas/detalhar/'+id, { headers }).pipe(
          //map((response) => response.content),
          tap((dieta) => {
            console.log('response recebido:', dieta);
            //console.log('Treino recebido:', treino);
            //localStorage.setItem('treino'+id, JSON.stringify(treino));
          }),
          catchError(this.handleError)
        );
      }

    getDietaSalva() {
      const dietas = localStorage.getItem('dietas');
      return dietas ? JSON.parse(dietas) : [];  
    }

    private handleError(error: HttpErrorResponse) {
        console.error('Erro na requisição:', error);
        return throwError(() => new Error('Erro ao realizar a requisição. Tente novamente mais tarde.'));
  }

  deleteDieta(token: string, id: number): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
    });
  
    return this.http.delete<void>(`${this.apiUrl}/dietas/excluir/${id}`, { headers }).pipe(
      tap(() => {
        console.log('Dieta excluído com sucesso!');
        // Se você quiser limpar os treinos salvos no localStorage após a exclusão
        // const dietasSalvas = this.getDietaSalva().filter(dieta => dieta.id !== id);
        // localStorage.setItem('dietas', JSON.stringify(dietasSalvas));
      }),
      catchError(this.handleError)
    );
  }
}
