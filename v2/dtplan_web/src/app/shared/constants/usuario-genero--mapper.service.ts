import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsuarioGeneroMapperService {
  private generoaMap: Record<string, string> = {
    'MASCULINO': 'Masculino',
    'FEMININO': 'Feminino',
    'OUTRO': 'Outro'
  };

  getGenero(tipo: string): string {
    return this.generoaMap[tipo] || 'Desconhecido';
  }
}
