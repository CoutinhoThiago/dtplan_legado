import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, tap, throwError, map } from 'rxjs';
import { CadastrarExecucaoDTO, ExecucaoDTO, ListarExecucaoDTO } from '../../models/execucao.dto';
import { CadastroTreinoDTO, EditarTreinoDTO, TreinoDTO, TreinoResponse } from '../../models/treino.dto';
import { formatISO } from 'date-fns';
import { ExercicioDTO } from '../../models/exercicio.dto';

export interface ExercicioResponse {
  content: ExercicioDTO[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  empty: boolean;
}


export interface FichaExercicioDTO {
  id: number;
  ordem: number
  carga: number;
  repeticoes: number;
  duracao_minutos: number;
  intensidade: number;
  series: number;
  exercicio: ExercicioDTO;
}

export interface CadastrarFichaExercicioDTO{
  fichaId: number;
  exercicioId: number;
  carga: number;
  repeticoes: number;
  duracao_minutos: number;
  intensidade: number;
  series: number;
}

export interface EditarFichaExercicioDTO {id: number;
  fichaId:number;
  exercicioId: number;

  ordem: number
  repeticoes: number;
  series: number;
  carga: number;

  duracao_minutos: number;
  intensidade: number;
}



@Injectable({
  providedIn: 'root'
})
export class TreinoService {
  private apiUrl = 'http://localhost:8081'; // URL do backend

  private http = inject(HttpClient);
  private router = inject(Router);

  // Obtém a lista de treinos
  getTreinos(token: string): Observable<TreinoDTO[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
    });
  
    return this.http.get<TreinoResponse>(this.apiUrl + '/treinos/listar', { headers }).pipe(
      // Extrai o conteúdo da resposta (lista de treinos)
      map((response: TreinoResponse) => response.content),
      tap((treinos: TreinoDTO[]) => {
        console.log('Treinos recebidos:', treinos);
        localStorage.setItem('treinos', JSON.stringify(treinos)); // Armazena os treinos no localStorage
      }),
      catchError(this.handleError)
    );
  }

  // Obtém os detalhes de um treino específico
  getTreino(token: string, id: number): Observable<TreinoDTO> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
    });

    return this.http.get<TreinoDTO>(this.apiUrl + '/treinos/detalhar/' + id, { headers }).pipe(
      tap((treino) => {
        console.log('Treino recebido:', treino);
      }),
      catchError(this.handleError)
    );
  }

  getExeciciios(token: string): Observable<ExercicioDTO[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
    });
  
    return this.http.get<ExercicioResponse>(this.apiUrl + '/exercicios/listar', { headers }).pipe(
      // Extrai o conteúdo da resposta (lista de treinos)
      map((response: ExercicioResponse) => response.content),
      tap((treinos: ExercicioDTO[]) => {
        console.log('Treinos recebidos:', treinos);
        localStorage.setItem('treinos', JSON.stringify(treinos)); // Armazena os treinos no localStorage
      }),
      catchError(this.handleError)
    );
  }

  // Cria um novo treino
  postTreino(token: string, treinoDTO: CadastroTreinoDTO): Observable<CadastroTreinoDTO> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    const body = {
      nome: treinoDTO.nome,
      descricao: treinoDTO.descricao,
      autor: { login: treinoDTO.autor.email },
      usuario: { id: treinoDTO.usuario.id }
    };

    return this.http.post<CadastroTreinoDTO>(this.apiUrl + '/treinos/criar', body, { headers }).pipe(
      tap((treino) => {
        console.log('Treino cadastrado:', treino);
      }),
      catchError((error: HttpErrorResponse) => {
        console.error('Erro ao cadastrar treino:', error);
        return throwError(() => new Error('Erro ao cadastrar treino. Tente novamente mais tarde.'));
      })
    );
  }


  // Edita um treino existente
  putTreino(token: string, id: number, treinoDTO: EditarTreinoDTO): Observable<EditarTreinoDTO> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` // Envia o token no cabeçalho
    });

    return this.http.put<EditarTreinoDTO>(this.apiUrl + `/treinos/editar/${id}`, treinoDTO, { headers }).pipe(
      tap((treino) => {
        console.log('Treino atualizado:', treino);
      }),
      catchError(this.handleError)
    );
  }


  postFichaExercicio(token: string, fichaExercicio: CadastrarFichaExercicioDTO): Observable<FichaExercicioDTO> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
    });

    return this.http.post<FichaExercicioDTO>(`${this.apiUrl}/fichaExercicio/criar`, fichaExercicio, { headers }).pipe(
      tap((response) => {
        console.log('FichaExercicio criada com sucesso:', response);
      }),
      catchError(this.handleError)
    );
  }

  putFichaExercicio(token: string, id: number, fichaExercicio: FichaExercicioDTO): Observable<FichaExercicioDTO> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
    })

    return this.http.put<FichaExercicioDTO>(`${this.apiUrl}/fichaExercicio/editar/${id}`, fichaExercicio, { headers }).pipe(
      tap(() => {
        console.log('Treino excluído com sucesso!');
        // Remove o treino excluído do localStorage
        const treinosSalvos = this.getTreinosSalvos().filter(treino => treino.id !== fichaExercicio.id);
        localStorage.setItem('treinos', JSON.stringify(treinosSalvos));
      }),
      catchError(this.handleError)
    );
  }

  // Exclui um treino
  deleteTreino(token: string, id: number): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
    });

    return this.http.delete<void>(`${this.apiUrl}/treinos/excluir/${id}`, { headers }).pipe(
      tap(() => {
        console.log('Treino excluído com sucesso!');
        // Remove o treino excluído do localStorage
        const treinosSalvos = this.getTreinosSalvos().filter(treino => treino.id !== id);
        localStorage.setItem('treinos', JSON.stringify(treinosSalvos));
      }),
      catchError(this.handleError)
    );
  }

  deleteFichaExercicio(token: string, id: number): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Envia o token no cabeçalho
    });

    return this.http.delete<void>(`${this.apiUrl}/fichaExercicio/excluir/${id}`, { headers }).pipe(
      tap(() => {
        console.log('Ficha excluída com sucesso!');

        const treinosSalvos = this.getTreinosSalvos().filter(treino => treino.id !== id);
        localStorage.setItem('treinos', JSON.stringify(treinosSalvos));
      }),
      catchError(this.handleError)
    );
  }

  reordenarFichaExercicios(token: string, exercicios: FichaExercicioDTO[]): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  
    return this.http.put<void>(`${this.apiUrl}/fichaExercicio/reordenar`, exercicios, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  // Obtém os treinos salvos no localStorage
  getTreinosSalvos(): TreinoDTO[] {
    const treinos = localStorage.getItem('treinos');
    return treinos ? JSON.parse(treinos) : [];
  }

  // Manipula erros das requisições HTTP
  private handleError(error: HttpErrorResponse) {
    console.error('Erro na requisição:', error);
    return throwError(() => new Error('Erro ao realizar a requisição. Tente novamente mais tarde.'));
  }

getExecucoesPorFicha(token: string, fichaId: number): Observable<ListarExecucaoDTO[]> {
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  });

  return this.http.get<ListarExecucaoDTO[]>(
    `${this.apiUrl}/execucoes/por-ficha/${fichaId}`, 
    { headers }
  ).pipe(
    catchError(this.handleError)
  );
}

cadastrarExecucao(token: string, execucaoData: CadastrarExecucaoDTO): Observable<ExecucaoDTO> {
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  });

  const dataISO = formatISO(new Date()); // Usa data atual

  // Formatar os dados para corresponder ao backend
  const requestBody = {
    treinoId: execucaoData.treinoId,
    fichaId: execucaoData.fichaId,

    data: dataISO,
    observacao: execucaoData.observacao,

    execucaoExercicios: execucaoData.exercicios.map(ex => ({
      id:  ex.id,
      seriesRealizadas: ex.seriesRealizadas,
      repeticoesRealizadas: ex.repeticoes,
      peso: ex.carga,

      exercicioId:  ex.exercicioId,
      exercicioNome:  ex.exercicioNome,
    })),
  };

  return this.http.post<ExecucaoDTO>(`${this.apiUrl}/execucoes/criar`, requestBody, { headers }).pipe(
    tap((response) => {
      console.log(requestBody)
      console.log('Execução cadastrada com sucesso:', response);
    }),
    catchError((error: HttpErrorResponse) => {
      console.error('Erro ao cadastrar execução:', error);
      return throwError(() => new Error('Erro ao cadastrar execução. Tente novamente mais tarde.'));
    })
  );
}

getExecucoesPorTreino(token: string, treinoId: number): Observable<ListarExecucaoDTO[]> {
  return this.http.get<ListarExecucaoDTO[]>(`${this.apiUrl}/execucoes/por-treino/${treinoId}`, {
    headers: { 'Authorization': `Bearer ${token}` }
  });
}

getEstatisticasTreino(token: string, treinoId: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/execucoes/estatisticas/${treinoId}`, {
    headers: { 'Authorization': `Bearer ${token}` }
  });
}

}