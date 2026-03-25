export interface ExecucaoExercicioDTO {
  id: number;
  seriesRealizadas: number;
  repeticoes: number;
  carga: number;
  exercicioId: number;
  exercicioNome: string;
}

export interface ListarExecucaoExercicioDTO {
  id: number;
  seriesPrevistas: number;
  seriesRealizadas: number;
  repeticoesPrevistas: number;
  repeticoesRealizadas: number;
  carga: number;
  exercicioId: number;
  exercicioNome: string;
}