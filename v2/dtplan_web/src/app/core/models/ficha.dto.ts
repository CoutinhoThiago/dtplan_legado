import { ExercicioDTO } from "./exercicio.dto";
import { EditarFichaExercicioDTO, FichaExercicioDTO } from "./fichaExercicio.dto";

export interface FichaDTO {
  id: number;
  dataCriacao: string;
  treinoId: number;
  nome: string;
  fichaExercicios: FichaExercicioDTO[];
  isExpanded: boolean;
  //exercicio: ExercicioDTO;
}

export interface CadastrarFichaDTO {
  treinoId: number;
  nome: string;
}

export interface EditarFichaDTO {
  nome: string;
  treinoId: number;
  fichaExercicios: EditarFichaExercicioDTO[];
}
