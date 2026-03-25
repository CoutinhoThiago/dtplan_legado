CREATE TABLE refeicao_alimento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    refeicao_id BIGINT NOT NULL,
    alimento_id BIGINT NOT NULL,
    quantidade DOUBLE,
    FOREIGN KEY (refeicao_id) REFERENCES refeicoes(id),
    FOREIGN KEY (alimento_id) REFERENCES alimento(id)
);