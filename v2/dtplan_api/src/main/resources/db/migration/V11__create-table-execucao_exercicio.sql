CREATE TABLE execucao_exercicio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ordem INT,
    seriesRealizadas INT,
    repeticoesRealizadas INT,
    peso DOUBLE,
    observacao TEXT,
    execucao_id BIGINT,
    exercicio_id BIGINT,
    ficha_id BIGINT,
    FOREIGN KEY (execucao_id) REFERENCES execucao(id),
    FOREIGN KEY (exercicio_id) REFERENCES exercicios(id),
    FOREIGN KEY (ficha_id) REFERENCES fichas(id)
);