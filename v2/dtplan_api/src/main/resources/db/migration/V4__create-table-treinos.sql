CREATE TABLE treinos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    autor_id BIGINT,
    usuario_id BIGINT,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);