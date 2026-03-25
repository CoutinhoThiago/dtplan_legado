import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DietaTipoMapperService {
  private tipoDietaMap: Record<string, string> = {
    'GANHO_MASSA': 'Ganho de Massa',
    'EMAGRECIMENTO': 'Emagrecimento',
    'MANUTENCAO': 'Manutenção'
  };

  getTipoDieta(tipo: string): string {
    return this.tipoDietaMap[tipo] || 'Desconhecido';
  }
}
