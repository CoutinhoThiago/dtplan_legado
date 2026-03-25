CREATE TABLE refeicoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT,
    dieta_id BIGINT,
    calorias DOUBLE,
    proteina DOUBLE,
    gordura DOUBLE,
    carboidrato DOUBLE,
    fibraAlimentar DOUBLE,
    FOREIGN KEY (dieta_id) REFERENCES dietas(id)
);