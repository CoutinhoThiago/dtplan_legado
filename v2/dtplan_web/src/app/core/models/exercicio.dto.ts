export interface ExercicioDTO {
  id: number;
  nome: string;
  ativo: boolean;
  tipo: number;
  observacao: string;
}

export interface ExercicioResponse {
  content: ExercicioDTO[];
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

export interface exerciciosDTO {
  id: number;
  nome: string;
  //musculo_alvo: string;
}