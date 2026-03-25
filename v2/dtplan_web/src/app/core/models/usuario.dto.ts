export interface UsuarioDTO {
  id: number,
  email: string;
  senha: string;
  //permissao: string;
  nome: string;
  cpf: string;
  tipoUsuario: string;
  crn: string;
  cref: string;
  alunos: AlunoDTO[];
  dataNascimento: string,
  genero: string,
  altura: number,
  pesoAtual: number
}

export interface AlunoDTO {
  id: number,
  nome: string,
  cpf: string,
  dataNascimento: string,
  altura: number,
  pesoAtual: number,
  nivelAtividade: string,
  objetivo: string,
  tipoUsuario: string,
  cref: string,
  crn: string
}

export interface EditarUsuarioDTO {
  email: string;
  senha: string;
  //permissao: string;
  nome: string;
  cpf: string;
  tipoUsuario: string;
  crn: string;
  cref: string;
  //dataNascimento: string,
  genero: string,
  altura: number,
  pesoAtual: number
  dataNascimento: string
}
export enum Genero {
  Masculino = 'masculino',
  Feminino = 'feminino',
  Outro = 'outro'
}


export interface CadastroDTO {
  email: string;
  senha: string;
  //permissao: string;
  nome: string;
  cpf: string;
  tipoUsuario: string;
  crn: string;
  cref: string;
  //genero: string,
  //altura: number,
  //pesoAtual: number
  //dataNascimento: String
}