-- Insere o usuário com a senha encriptada na tabela
INSERT INTO usuarios (login, senha, permissao, nome, cpf)
VALUES ('tcoutinhossilva@gmail.com', '$2a$10$6s3MXtAiawHWvJKPB1UQfuf4FMDGFU6ttTjiQhSda9RLxrQEJx/aa', 'USER', 'Nome do Usuário', '08866966584');

-- Inserir treinno de musculação
INSERT INTO dtplan_api.treinos (descricao, autor, tipo, usuario_id) VALUES ('Treino de Musculação', 'Eu', 'MUSCULACAO', 1);

-- Inserção de valores na tabela de ficha
INSERT INTO dtplan_api.fichas (treino_id, nome) VALUES
    ((SELECT id FROM dtplan_api.treinos WHERE tipo = 'MUSCULACAO'), 'Peito, Ombro e Tríceps - Segunda-feira'), -- ID: 1
    ((SELECT id FROM dtplan_api.treinos WHERE tipo = 'MUSCULACAO'), 'Costas e Bíceps - Terça-feira'), -- ID: 2
    ((SELECT id FROM dtplan_api.treinos WHERE tipo = 'MUSCULACAO'), 'Perna - Quarta-feira'), -- ID: 3
    ((SELECT id FROM dtplan_api.treinos WHERE tipo = 'MUSCULACAO'), 'Peito, Ombro e Tríceps - Quinta-feira'), -- ID: 4
    ((SELECT id FROM dtplan_api.treinos WHERE tipo = 'MUSCULACAO'), 'Costas e Bíceps - Sexta-feira'), -- ID: 5
    ((SELECT id FROM dtplan_api.treinos WHERE tipo = 'MUSCULACAO'), 'Perna - Sábado'); -- ID: 6

-- Inserção dos exercícios na ficha de treino 1 (Segunda-feira - Peito + Ombro + Tríceps + Abdômen)
INSERT INTO dtplan_api.fichas_exercicios (fichas_id, exercicios_id) VALUES
    (1, 1),   -- Supino inclinado com halter
    (1, 2),   -- Supino reto com barra
    (1, 3),   -- Crosolver polia alta
    (1, 8),   -- Desenvolvimento com halter
    (1, 9),   -- Elevação lateral com halter
    (1, 10),  -- Elevação lateral no cabo
    (1, 11),  -- Crucifixo inverso com halter
    (1, 12),  -- Triceps pulley
    (1, 13),  -- Tríceps testa
    (1, 14),  -- Tríceps francês
    (1, 15),  -- Abdominal máquina
    (1, 16);  -- Abdominal declinado

-- Inserção dos exercícios na ficha de treino 2 (Terça-feira - Costas + Bíceps + Antebraço)
INSERT INTO dtplan_api.fichas_exercicios (fichas_id, exercicios_id) VALUES
    (2, 4),   -- Puxador frente pegada pronada
    (2, 5),   -- Remada baixa
    (2, 6),   -- Remada curvado pronada
    (2, 7),   -- Remada com halter
    (2, 8),   -- Encolhimento de trapézio
    (2, 9),   -- Levantamento terra
    (2, 34),  -- Rosca direta na polia
    (2, 35),  -- Rosca 45°
    (2, 37),  -- Rosca scott
    (2, 36);  -- Rosca martelo

-- Inserção dos exercícios na ficha de treino 3 (Quarta-feira - Perna)
INSERT INTO dtplan_api.fichas_exercicios (fichas_id, exercicios_id) VALUES
    (3, 19),  -- Agachamento Smith
    (3, 20),  -- Agachamento Smith
    (3, 21),  -- Agachamento Smith
    (3, 22),  -- Agachamento Smith
    (3, 23),  -- Leg Press
    (3, 24),  -- Cadeira Extensora
    (3, 25),  -- Cadeira Abdutora
    (3, 26),  -- Cadeira Abdutora Inclinada
    (3, 27),  -- Elevação Pélvica
    (3, 28),  -- Cadeira Flexora Unilateral
    (3, 29),  -- Cadeira Flexora
    (3, 30),  -- Panturrilha Sentado
    (3, 31),  -- Panturrilha em Pé Máquina
    (3, 32),  -- Stiff
    (3, 33);  -- Mesa Flexora

-- Inserção dos exercícios na ficha de treino 4 (Quinta-feira - Peito + Tríceps)
INSERT INTO dtplan_api.fichas_exercicios (fichas_id, exercicios_id) VALUES
    (4, 1),   -- Supino inclinado com halter
    (4, 2),   -- Supino reto com barra
    (4, 3),   -- Crosolver polia alta
    (4, 8),   -- Elevação lateral com halter
    (4, 9),   -- Elevação lateral no cabo
    (4, 12),  -- Triceps pulley
    (4, 13),  -- Triceps testa
    (4, 14),  -- Tríceps francês
    (4, 18),  -- Supino fechado
    (4, 16),  -- Abdominal declinado
    (4, 17);  -- Abdominal curta

-- Inserção dos exercícios na ficha de treino 5 (Sexta-feira - Costas + Bíceps + Antebraço)
INSERT INTO dtplan_api.fichas_exercicios (fichas_id, exercicios_id) VALUES
    (5, 4),   -- Puxador frente pegada pronada
    (5, 5),   -- Remada baixa
    (5, 6),   -- Remada curvado pronada
    (5, 7),   -- Remada com halter
    (5, 10),  -- Crucifixo inverso com halter
    (5, 34),  -- Rosca direta na polia
    (5, 37),  -- Rosca scott
    (5, 35),  -- Rosca 45°
    (5, 39),  -- Rosca punho
    (5, 40),  -- Stiff (Antebraço)
    (5, 18);  -- Abdominal declinado

-- Inserção dos exercícios no treino 6 (Perna - Sábado)
INSERT INTO dtplan_api.fichas_exercicios (fichas_id, exercicios_id) VALUES
    (6, 19), -- Agachamento Smith
    (6, 23), -- Leg Press
    (6, 24), -- Cadeira Extensora
    (6, 25), -- Cadeira Abdutora
    (6, 26), -- Cadeira Abdutora Inclinada
    (6, 27), -- Elevação Pélvica
    (6, 32), -- Stiff
    (6, 33), -- Mesa Flexora
    (6, 30), -- Panturrilha Sentado
    (6, 31); -- Panturrilha em Pé Máquina