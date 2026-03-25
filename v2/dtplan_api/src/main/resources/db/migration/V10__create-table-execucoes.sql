CREATE TABLE execucao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data DATE,
    observacao TEXT,
    treino_id BIGINT,
    ficha_id BIGINT,
    FOREIGN KEY (treino_id) REFERENCES treinos(id),
    FOREIGN KEY (ficha_id) REFERENCES fichas(id)
);