import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable, of, throwError } from 'rxjs';

export interface CadastroDTO {
  email: string;
  senha: string;
  //permissao: string;
  nome: string;
  cpf: string;
  tipoUsuario: string;
   crn: string;
   cref: string;
}

export interface LoginDTO {
  email: string;
  senha: string;
}

export interface TokenDTO {
  token: string;
  refreshToken: string;
  id: number;
  nome: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081'; // URL do backend

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, senha: string): Observable<TokenDTO> {
    const loginData: LoginDTO = { email, senha };
    return this.http.post<TokenDTO>(`${this.apiUrl}/auth/login`, loginData).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('usuario', JSON.stringify({ id: response.id, nome: response.nome }));
        localStorage.setItem('login', email);
      }),
      catchError(this.handleError)
    );
  }

  validarToken(token: string): Observable<boolean> {
    return this.http.post<{ isValid: boolean }>(`${this.apiUrl}/auth/token`, { token }).pipe(
      map((response) => response.isValid),
      catchError(this.handleError)
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string {
    return localStorage.getItem('token') || '';
  }

  getUsuario(): { id: number; nome: string } | null {
    const usuario = localStorage.getItem('usuario');
    return usuario ? JSON.parse(usuario) : null;
  }

  private handleError(error: HttpErrorResponse) {
    console.error('Erro na requisição:', error);
    return throwError(() => new Error('Erro ao realizar a requisição. Tente novamente mais tarde.'));
  }
}