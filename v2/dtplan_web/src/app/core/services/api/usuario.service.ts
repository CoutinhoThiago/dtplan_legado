import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable, of, throwError } from 'rxjs';
import { CadastroDTO, TokenDTO } from './auth.service';
import { EditarUsuarioDTO, UsuarioDTO } from '../../models/usuario.dto';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8081'; // URL do backend

  constructor(private http: HttpClient, private router: Router) { }


  cadastro(email: string, senha: string, nome: string, cpf: string, tipoUsuario: string, crn: string, cref: string): Observable<TokenDTO> {
    const cadastroData: CadastroDTO = { email, senha, nome, cpf, tipoUsuario, crn, cref };
    return this.http.post<TokenDTO>(`${this.apiUrl}/usuarios/criar`, cadastroData).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('usuario', JSON.stringify({ id: response.id, nome: response.nome }));
        localStorage.setItem('login', email);
      }),
      catchError(this.handleError)
    );
  }

  consultarUsuario(token: string): Observable<UsuarioDTO> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<any>(`${this.apiUrl}/usuarios/detalhar`, { headers }).pipe(
      map((response) => response.body), // Extrai o objeto "body" da resposta
      tap((usuario) => {
        console.log('Usuário recebido:', usuario);
        localStorage.setItem('usuario', JSON.stringify(usuario)); // Armazena apenas o "body"
      }),
      catchError(this.handleError)
    );
  }

  atualizarUsuario(usuario: EditarUsuarioDTO): Observable<TokenDTO> {
    const token = localStorage.getItem('token');

    if (!token) {
      throw new Error('Token não encontrado! O usuário precisa estar autenticado.');
    }

    const headers = { Authorization: `Bearer ${token}` };

    return this.http.put<TokenDTO>(`${this.apiUrl}/usuarios/editar`, usuario, { headers }).pipe(
      tap((response) => {
        console.log("Usuario editado", response)
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.error('Erro na requisição:', error);
    return throwError(() => new Error('Erro ao realizar a requisição. Tente novamente mais tarde.'));
  }
}