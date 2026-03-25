import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { formatISO } from 'date-fns';
import { catchError, Observable, throwError } from 'rxjs';
import { CadastrarExecucaoDTO, ExecucaoDTO, ListarExecucaoDTO } from '../../../models/execucao.dto';

@Injectable({ providedIn: 'root' })
export class ExecucaoService {
  private apiUrl = 'http://localhost:8081';
  private http = inject(HttpClient);

  getExecucoesPorFicha(token: string, fichaId: number): Observable<ListarExecucaoDTO[]> {
    const headers = new HttpHeaders({ 
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.get<ListarExecucaoDTO[]>(
      `${this.apiUrl}/execucoes/por-ficha/${fichaId}`, 
      { headers }
    );
  }

  cadastrarExecucao(token: string, execucaoData: CadastrarExecucaoDTO): Observable<ExecucaoDTO> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    const requestBody = {
      treinoId: execucaoData.treinoId,
      fichaId: execucaoData.fichaId,
      data: formatISO(new Date()),
      observacao: execucaoData.observacao,
      execucaoExercicios: execucaoData.exercicios.map(ex => ({
        id: ex.id,
        seriesRealizadas: ex.seriesRealizadas,
        repeticoesRealizadas: ex.repeticoes,
        peso: ex.carga,
        exercicioId: ex.exercicioId,
        exercicioNome: ex.exercicioNome,
      })),
    };

    return this.http.post<ExecucaoDTO>(`${this.apiUrl}/execucoes/criar`, requestBody, { headers });
  }

  getExecucoesPorTreino(token: string, treinoId: number): Observable<ListarExecucaoDTO[]> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.get<ListarExecucaoDTO[]>(
      `${this.apiUrl}/execucoes/por-treino/${treinoId}`, 
      { headers }
    );
  }

  getEstatisticasTreino(token: string, treinoId: number): Observable<any> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.get<any>(
      `${this.apiUrl}/execucoes/estatisticas/${treinoId}`, 
      { headers }
    );
  }
}