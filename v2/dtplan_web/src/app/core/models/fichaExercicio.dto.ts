import { ExercicioDTO, exerciciosDTO } from "./exercicio.dto";

export interface EditarFichaExercicioDTO {
  id: number;
  fichaId: number;
  exercicioId: number;
  ordem: number;
  repeticoes: number;
  series: number;
  carga: number;
  duracao_minutos: number;
  intensidade: number;
}

export interface FichaExercicioDTO {
  id: number;
  ordem: number;
  carga: number;
  repeticoes: number;
  duracao_minutos: number;
  intensidade: number;
  series: number;
  exercicio: ExercicioDTO;
}

export interface CadastrarFichaExercicioDTO {
  fichaId: number;
  exercicioId: number;
  carga: number;
  repeticoes: number;
  duracao_minutos: number;
  intensidade: number;
  series: number;
}