CREATE TABLE dietas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT,
    autor VARCHAR(255),
    tipo VARCHAR(20) CHECK (tipo IN ('PERDA_PESO', 'GANHO_MASSA', 'MANTER_PESO','GANHO_MUSCULAR','HIPERTROFIA','OUTRO')),
    usuario_id BIGINT,
    calorias FLOAT,
    proteina FLOAT,
    gordura FLOAT,
    carboidrato FLOAT,
    fibraAlimentar FLOAT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);