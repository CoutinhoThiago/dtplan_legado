CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    permissao ENUM('ADMIN','USER') NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    tipoUsuario VARCHAR(50),
    cref VARCHAR(50),
    crn VARCHAR(50),
    data_nascimento VARCHAR(50),
    altura INT,
    peso_atual INT,
    nivel_atividade ENUM('SEDENTARIO', 'LEVE', 'MODERADO', 'INTENSO', 'MUITO_INTENSO', 'LEVEMENTE_ATIVO','MODERADAMENTE_ATIVO','MUITO_ATIVO','EXTREMAMENTE_ATIVO'),
    objetivo ENUM('PERDA_PESO', 'MANUTENCAO', 'GANHO_MASSA', 'MANTER_PESO','GANHO_MUSCULAR','MELHORAR_CONDICIONAMENTO'),
    genero ENUM('MASCULINO','FEMININO','OUTRO')
);

-- Tabelas de relacionamento many-to-many
CREATE TABLE treinador_alunos (
    treinador_id BIGINT NOT NULL,
    aluno_id BIGINT NOT NULL,
    PRIMARY KEY (treinador_id, aluno_id),
    FOREIGN KEY (treinador_id) REFERENCES usuarios(id),
    FOREIGN KEY (aluno_id) REFERENCES usuarios(id)
);

CREATE TABLE nutricionista_pacientes (
    nutricionista_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    PRIMARY KEY (nutricionista_id, paciente_id),
    FOREIGN KEY (nutricionista_id) REFERENCES usuarios(id),
    FOREIGN KEY (paciente_id) REFERENCES usuarios(id)
);

