import { FichaDTO } from "./ficha.dto";
import { UsuarioDTO } from "./usuario.dto";

export interface TreinoDTO {
  id: number;
  nome: string;
  descricao: string;
  autor: UsuarioDTO;
  usuario: UsuarioDTO;
  fichas: FichaDTO[];
}

export interface TreinoResponse {
  content: TreinoDTO[];
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

export interface CadastroTreinoDTO {
  nome: string;
  descricao: string;
  autor: UsuarioDTO;
  usuario: UsuarioDTO;
}

export interface EditarTreinoDTO {
  nome: string;
  descricao: string;
  autor: UsuarioDTO;
  usuario: UsuarioDTO;
}