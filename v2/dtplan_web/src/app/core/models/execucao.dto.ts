// src/app/core/models/execucao.dto.ts
import { ExecucaoExercicioDTO, ListarExecucaoExercicioDTO } from "./execucaoExercicio.dto";
import { FichaDTO } from "./ficha.dto";
import { UsuarioDTO } from "./usuario.dto";

export interface ExecucaoDTO {
  id: number;
  dataInicio: string;
  dataFim: string;
  tempoTotal: number;
  observacoes: string;
  fichaId: number;
  nomeFicha?: string;
  execucaoExercicios: ExecucaoExercicioDTO[];
}

export interface CadastrarExecucaoDTO {
  treinoId: number;
  fichaId: number;
  exercicios: ExecucaoExercicioDTO[];
  observacao: string;
}

export interface ListarExecucaoDTO {
  id: number;
  data: string;
  dataFim?: string;
  tempoTotal: number;
  observacoes?: string;
  fichaId: number;
  treinoId: number;
  nomeFicha: string;
  execucaoExercicios: ListarExecucaoExercicioDTO[];
}