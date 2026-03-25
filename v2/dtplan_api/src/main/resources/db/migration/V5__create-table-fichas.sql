CREATE TABLE fichas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    treino_id BIGINT,
    FOREIGN KEY (treino_id) REFERENCES treinos(id)
);