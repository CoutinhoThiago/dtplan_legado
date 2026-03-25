CREATE TABLE ficha_exercicio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ficha_id BIGINT NOT NULL,
    exercicio_id BIGINT NOT NULL,
    repeticoes INT,
    series INT,
    carga DOUBLE,
    duracao_minutos INT,
    intensidade INT,
    ordem INT,
    FOREIGN KEY (ficha_id) REFERENCES fichas(id),
    FOREIGN KEY (exercicio_id) REFERENCES exercicios(id)
);